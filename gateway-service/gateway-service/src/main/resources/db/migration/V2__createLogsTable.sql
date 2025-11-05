CREATE TABLE logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255),
    content TEXT,
    author_id UUID,
    created_at TIMESTAMP
);
 