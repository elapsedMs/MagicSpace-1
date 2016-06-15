package storm.commonlib.common.util;

/**
 * Created by yongjiu on 15/8/15.
 */
public enum ImageSize {

    S40x40("40x40"),
    S80x80("80x80"),
    S100x100("100x100"),
    S480x320("480x320"),
    S800x600("800x600"),
    S800x400("800x400"),
    S200x200("200x200"),
    S320x210("320x210"),
    S400x400("400x400"),
    S320x360s("320x360s"),
    S640x420s("640x420s"),
    Orig("orig"),
    S256x256("256x256"),

//    S40x40("40x40"),
//    S80x80("80x80"),
//    S100x100("100x100"),
//    S480x320("480x320"),
//    S800x600("480x320"),
//    S800x400("800x400"),
//    S200x200("200x200"),
//    S320x210("320x210"),
//    S400x400("400x400"),
//    S320x360s("320x360s"),
//    S640x420s("640x420s"),
//    Orig("orig"),

    // Preview Sizes
    PostSingle(ImageSize.S640x420s),
    PostMult(ImageSize.S400x400),
    Article(ImageSize.S320x210),
    Big(ImageSize.S800x400),
    Thumbs(ImageSize.S200x200),
    Avatar(ImageSize.S400x400);

    private final String mSize;

    ImageSize(String size) {
        this.mSize = size;
    }

    ImageSize(ImageSize size) {
        this(size.mSize);
    }

    @Override
    public String toString() {
        return this.mSize;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}