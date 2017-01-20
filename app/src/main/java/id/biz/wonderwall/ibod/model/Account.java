package id.biz.wonderwall.ibod.model;

/**
 * Created by winnerawan on 1/19/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {

        @SerializedName("extid")
        @Expose
        private String extid;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("signup_at")
        @Expose
        private String signupAt;
        @SerializedName("storage_left")
        @Expose
        private Integer storageLeft;
        @SerializedName("storage_used")
        @Expose
        private String storageUsed;
        @SerializedName("traffic")
        @Expose
        private Traffic traffic;
        @SerializedName("balance")
        @Expose
        private String balance;

        public String getExtid() {
            return extid;
        }

        public void setExtid(String extid) {
            this.extid = extid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSignupAt() {
            return signupAt;
        }

        public void setSignupAt(String signupAt) {
            this.signupAt = signupAt;
        }

        public Integer getStorageLeft() {
            return storageLeft;
        }

        public void setStorageLeft(Integer storageLeft) {
            this.storageLeft = storageLeft;
        }

        public String getStorageUsed() {
            return storageUsed;
        }

        public void setStorageUsed(String storageUsed) {
            this.storageUsed = storageUsed;
        }

        public Traffic getTraffic() {
            return traffic;
        }

        public void setTraffic(Traffic traffic) {
            this.traffic = traffic;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

    }
