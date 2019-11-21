package be.vives.ti.demospringmvc.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentRequest {

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String firstName;
    @NotNull
    @Past
    private LocalDate birthDate;
    @NotNull
    private Boolean signedUp;
    @NotNull
    @Min(0)
    private BigDecimal saldo;
    @NotNull
    @NotEmpty
    private String gender;

    // getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getSignedUp() {
        return signedUp;
    }

    public void setSignedUp(Boolean signedUp) {
        this.signedUp = signedUp;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
