package data.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter (AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Resources user;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resources {

        private String gender;
        private Name name;
        private Location location;

        @Getter(AccessLevel.PUBLIC)
        @Setter(AccessLevel.PUBLIC)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Name {
            private String middle;
            private String first;
            private String last;
        }

        @Getter(AccessLevel.PUBLIC)
        @Setter(AccessLevel.PUBLIC)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Location {
            private String street;
            private String city;
            private String state;
            private int zip;
            private int building;
        }
    }

}
