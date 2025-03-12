package Services;

import DTO.SignupRequest;
import Model.UserM;

public interface AuthServiceM {

	UserM createUserM(SignupRequest signupRequest);

}
