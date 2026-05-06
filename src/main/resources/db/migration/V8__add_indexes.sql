CREATE INDEX IF NOT EXISTS idx_status
ON compliance(status);

CREATE INDEX IF NOT EXISTS idx_title
ON compliance(title);

CREATE INDEX IF NOT EXISTS idx_due_date
ON compliance(due_date);
