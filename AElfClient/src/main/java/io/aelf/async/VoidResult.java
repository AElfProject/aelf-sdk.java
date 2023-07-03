package io.aelf.async;

// It's an AsyncResult that only contains resultCode
public final class VoidResult extends AsyncResult<Void>{
    public VoidResult(int resultCode) {
        super(resultCode);
    }

}
