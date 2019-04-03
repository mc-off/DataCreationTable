package data.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BirthDate {

    private List<Results> results;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {

        private Dob dob;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Dob {
            private String date;
            private int age;
        }
    }
}
