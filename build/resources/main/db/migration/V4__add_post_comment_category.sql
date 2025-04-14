CREATE TABLE IF NOT EXISTS category (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS post (
    post_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content JSONB NOT NULL,
    tags TEXT[] NOT NULL,
    category_id INT NOT NULL,
    tsv TSVECTOR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE IF NOT EXISTS comment (
   comment_id  SERIAL PRIMARY KEY,
   post_id INTEGER NOT NULL,
   -- the user_id may be null if the comment is anonymous
   user_id INTEGER,
   comment_text TEXT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
   FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--------------------------------------------------------
-- Drop the existing function if it exists
DROP FUNCTION IF EXISTS set_updated_at();

-- Function to update updated_at column
CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to call the function before any update
CREATE TRIGGER trigger_set_updated_at
    BEFORE UPDATE ON post
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Repeat for other tables if needed
CREATE TRIGGER trigger_set_updated_at_comment
    BEFORE UPDATE ON comment
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-----------------------------------------------------------------

-- Drop the existing function if it exists
DROP FUNCTION IF EXISTS update_blog_tsv();

-- Function to update tsvector with ranking (Tags > Title > content title >Text)
CREATE FUNCTION update_blog_tsv() RETURNS TRIGGER AS $$
DECLARE
    tags_text TEXT;
    content_texts TEXT;
    content_headers TEXT;

BEGIN
    -- Extract all titles and texts from JSONB content
    -- Extract and aggregate 'text' from the content JSONB array
    SELECT string_agg(value->>'text', ' ') INTO content_texts
    FROM jsonb_array_elements(NEW.content) AS value
    WHERE value->>'text' IS NOT NULL;

-- Extract and aggregate 'title' from the content JSONB array
    SELECT string_agg(value->>'title', ' ') INTO content_headers
    FROM jsonb_array_elements(NEW.content) AS value
    WHERE value->>'title' IS NOT NULL;

-- Convert array of tags to a space-separated string
    tags_text := array_to_string(NEW.tags, ' ');

    -- Set ranking: Tags ('A') > Title ('B') > Text ('C')
    NEW.tsv :=
            setweight(to_tsvector('simple', COALESCE(NEW.title, '')), 'A') ||
            setweight(to_tsvector('simple', COALESCE(tags_text, '')), 'B') ||
            setweight(to_tsvector('simple', COALESCE(content_headers, '')), 'C') ||
            setweight(to_tsvector('simple', COALESCE(content_texts, '')), 'D');

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

-- Trigger to update tsvector when content or tags change
CREATE TRIGGER blog_tsv_trigger
    BEFORE INSERT OR UPDATE ON post FOR EACH ROW EXECUTE FUNCTION update_blog_tsv();

-- Create a GIN index for fast full-text search
CREATE INDEX blog_tsv_idx ON post USING GIN(tsv);


