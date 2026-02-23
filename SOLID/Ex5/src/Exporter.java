public abstract class Exporter {
    /**
     * Base contract:
     * - {@code req} must be non-null; otherwise {@link IllegalArgumentException} is thrown.
     * - Implementations may throw {@link IllegalArgumentException} if the request cannot be exported
     *   due to format-specific constraints.
     * - On success returns a non-null {@link ExportResult} with non-null {@code bytes}.
     */
    public final ExportResult export(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("request cannot be null");
        ExportResult out = doExport(req);
        if (out == null) throw new IllegalStateException("export result cannot be null");
        if (out.bytes == null) throw new IllegalStateException("export bytes cannot be null");
        return out;
    }

    protected abstract ExportResult doExport(ExportRequest req);
}
