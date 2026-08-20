public class ReportExporterFactory {
    public static void main(String[] args) {
        String[] formats = {"CSV", "json", "xml", "TXT"};
        String title = "月度銷售數據";
        int[] salesData = {120, 250, 310, 450, 200};

        System.out.println("=== 測試正常數據輸出 ===");
        for (String fmt : formats) {
            ReportExporter exporter = createExporter(fmt);
            exportReport(exporter, title, salesData);
            System.out.println();
        }

        System.out.println("=== 測試 values 為 null 的狀況 ===");
        ReportExporter nullTestExporter = createExporter("CSV");
        exportReport(nullTestExporter, "空數據報表", null);
    }

    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }
        return switch (format.trim().toUpperCase()) {
            case "CSV" -> new CsvExporter();
            case "JSON" -> new JsonExporter();
            default -> new TextExporter();
        };
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            return;
        }
        exporter.export(title, values);
    }
}

interface ReportExporter {
    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("【CSV 格式報表】");
        System.out.println("Title: " + (title == null ? "" : title));
        System.out.print("Values: ");
        if (values == null || values.length == 0) {
            System.out.println("(無數據)");
            return;
        }
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + (i < values.length - 1 ? "," : ""));
        }
        System.out.println();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("【JSON 格式報表】");
        System.out.println("{");
        System.out.println("  \"title\": \"" + (title == null ? "" : title) + "\",");
        System.out.print("  \"values\": ");
        if (values == null) {
            System.out.println("[]");
        } else {
            System.out.print("[");
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? ", " : ""));
            }
            System.out.println("]");
        }
        System.out.println("}");
    }
}

class TextExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("【純文字格式報表】");
        System.out.println("=== " + (title == null ? "" : title) + " ===");
        if (values == null || values.length == 0) {
            System.out.println("數據：(無數據)");
            return;
        }
        for (int i = 0; i < values.length; i++) {
            System.out.println("項目 " + (i + 1) + ": " + values[i]);
        }
    }
}