public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument backupDoc = new BackupDocument("系統日誌備份_2026.log");

        Exportable exportableRef = backupDoc;
        Compressible compressibleRef = backupDoc;

        System.out.println("--- 透過 Exportable Reference 呼叫 ---");
        exportableRef.export("PDF");

        System.out.println("\n--- 透過 Compressible Reference 呼叫 ---");
        compressibleRef.compress("ZIP");

        System.out.println("\n--- 說明與驗證 ---");
        System.out.println("1. 記憶體位址/物件參考相同 (exportableRef == compressibleRef): " + (exportableRef == compressibleRef));
        System.out.println("2. 說明：這兩個 reference 實際上都指向記憶體中的同一物件 (BackupDocument)。");
        System.out.println("   但是由於宣告型態 (Reference Type) 不同，編譯器會根據 reference 的型態限制可呼叫的 API 方法。");
        System.out.println("   - Exportable reference 僅可見 export()。");
        System.out.println("   - Compressible reference 僅可見 compress()。");
    }
}

interface Exportable {
    void export(String format);
}

interface Compressible {
    void compress(String algorithm);
}

class BackupDocument implements Exportable, Compressible {
    private String fileName;

    public BackupDocument(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void export(String format) {
        System.out.println("正在將檔案 [" + fileName + "] 匯出為 " + format + " 格式...");
    }

    @Override
    public void compress(String algorithm) {
        System.out.println("正在使用 " + algorithm + " 演算法壓縮檔案 [" + fileName + "]...");
    }
}