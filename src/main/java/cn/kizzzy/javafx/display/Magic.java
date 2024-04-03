package cn.kizzzy.javafx.display;

public enum Magic {
    PNG(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47}),
    JPG(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0}),
    
    MP3(new byte[]{0x49, 0x44, 0x33, 0x03}),
    OGG(new byte[]{0x4F, 0x67, 0x67, 0x53}),
    WAV(new byte[]{0x52, 0x49, 0x46, 0x46}),
    
    PDF(new byte[]{0x25, 0x50, 0x44, 0x46}),
    ZIP(new byte[]{0x50, 0x4B, 0x03, 0x04}),
    
    DBBIN(new byte[]{0x44, 0x42, 0x44, 0x54}),
    ;
    
    private final byte[] magic;
    private final int length;
    
    Magic(byte[] magic) {
        this.magic = magic;
        this.length = magic.length;
    }
    
    public byte[] getMagic() {
        return magic;
    }
    
    public int getLength() {
        return length;
    }
}
