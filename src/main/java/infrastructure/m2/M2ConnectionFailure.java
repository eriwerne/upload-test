package infrastructure.m2;

import application.reading.exception.ResourceFailure;

class M2ConnectionFailure extends ResourceFailure {
    public M2ConnectionFailure(Throwable e) {
        super(e, "M2");
    }
}
