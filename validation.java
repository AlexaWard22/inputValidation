public class validation{
    public boolean validateUsername(String username) {
        return !containSQLInjectionChar(username);
    }
    public boolean validatePassword(String password){
        return !containSQLInjectionChar(password) && meetPasswordPolicy(password);
    }
    public boolean validateMFA(String mfaCode){
        try{
            int mfa = Integer.parseInt(mfaCode);
            return mfaCode.length() == 10 && mfa >= 0;
        }catch(NumberFormatException e){
            return false;
        }
    }
    private boolean containSQLInjectionChar(String input){
        String[] sqlInjectionChar = {"/","-",";","\""};
        for(String ch :sqlInjectionChar){
            if(input.contains(ch)){
                return true;
            }
        }
        return false;
    }
    private boolean meetPasswordPolicy(String password){
        if(password.length() < 8 || password.length() > 12){
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for(char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)) hasUpper = true;
            if(Character.isLowerCase(ch)) hasLower = true;
            if(Character.isDigit(ch)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }
}