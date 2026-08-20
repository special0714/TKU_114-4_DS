public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] mediaFiles = new MediaFile[] {
            new ImageFile("風景照.jpg", 12.5, "3840x2160"),
            new AudioFile("流行歌曲.mp3", 4.2, 215),
            new VideoFile("教學影片.mp4", 450.0, 1080, 1200)
        };

        for (MediaFile file : mediaFiles) {
            file.displayInfo();

            if (file instanceof Playable playable) {
                playable.play();
            }

            if (file instanceof Compressible compressible) {
                compressible.compress();
            }

            System.out.println("----------------------------------------");
        }
    }
}

abstract class MediaFile {
    private String fileName;
    private double fileSizeMB;

    public MediaFile(String fileName, double fileSizeMB) {
        this.fileName = fileName;
        this.fileSizeMB = fileSizeMB;
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public abstract void displayInfo();
}

interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

class ImageFile extends MediaFile implements Compressible {
    private String resolution;

    public ImageFile(String fileName, double fileSizeMB, String resolution) {
        super(fileName, fileSizeMB);
        this.resolution = resolution;
    }

    @Override
    public void displayInfo() {
        System.out.println("【圖片檔案】檔名: " + getFileName() + " | 大小: " + getFileSizeMB() + " MB | 解析度: " + resolution);
    }

    @Override
    public void compress() {
        System.out.println("-> 執行圖片壓縮：降低解析度與優化 JPEG 編碼。");
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    private int durationSeconds;

    public AudioFile(String fileName, double fileSizeMB, int durationSeconds) {
        super(fileName, fileSizeMB);
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void displayInfo() {
        System.out.println("【音訊檔案】檔名: " + getFileName() + " | 大小: " + getFileSizeMB() + " MB | 時長: " + durationSeconds + " 秒");
    }

    @Override
    public void play() {
        System.out.println("-> 播放音訊：解碼 MP3 / AAC 音軌並輸出至揚聲器。");
    }

    @Override
    public void compress() {
        System.out.println("-> 執行音訊壓縮：調降 Bitrate 至 128 kbps。");
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private int resolutionHeight;
    private int durationSeconds;

    public VideoFile(String fileName, double fileSizeMB, int resolutionHeight, int durationSeconds) {
        super(fileName, fileSizeMB);
        this.resolutionHeight = resolutionHeight;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void displayInfo() {
        System.out.println("【影片檔案】檔名: " + getFileName() + " | 大小: " + getFileSizeMB() + " MB | 畫質: " + resolutionHeight + "p | 時長: " + durationSeconds + " 秒");
    }

    @Override
    public void play() {
        System.out.println("-> 播放影片：渲染影像畫面並同步播放音訊軌。");
    }

    @Override
    public void compress() {
        System.out.println("-> 執行影片壓縮：使用 H.265 編碼進行二次壓縮。");
    }
}