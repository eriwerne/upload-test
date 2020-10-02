package infrastructure.m2;

import application.reading.exception.ResourceFailure;

public interface M2Api {
    String getArticleString(String articleNumber) throws ResourceFailure;
}
