package infrastructure.mmp;

import application.reading.exception.ResourceFailure;

class MmpConnectionFailure extends ResourceFailure {
    public MmpConnectionFailure(Throwable e) {
        super(e, "MMP");
    }
}
