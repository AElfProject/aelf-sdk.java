package io.aelf.async;

// It's an AsyncResult that only contains resultCode
final class VoidResult extends AsyncResult<Void>{
    public VoidResult(int resultCode) {
        super(resultCode);
    }
}
