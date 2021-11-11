package com.poppinstack.mailtology.constant;

public enum MAILGUN_DOMAIN_REGION {
        US("us"),
        EU("eu");
        private String name;
        MAILGUN_DOMAIN_REGION(String name){
            this.name = name;
        }

        public static MAILGUN_DOMAIN_REGION lookup(String region){
            if("us".equals(region)){
                return US;
            }else if("eu".equals(region)){
                return EU;
            }
            return null;
        }
    }

