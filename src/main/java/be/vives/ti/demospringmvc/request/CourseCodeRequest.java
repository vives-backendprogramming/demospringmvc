package be.vives.ti.demospringmvc.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseCodeRequest {

    @NotNull
    @NotEmpty
    @Size(min=6, max = 6)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
