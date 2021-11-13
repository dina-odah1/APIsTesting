package utils.configuration;

import java.util.Properties;

public class ConfigLoader {
        private final Properties properties;
        private static ConfigLoader configLoader;

        public ConfigLoader(){
            properties = GlobalProperties.propertyLoader("src/test/resources/globalvariables.properties");
        }

        public static ConfigLoader getInstance(){
            if(configLoader == null){
                configLoader = new ConfigLoader();
            }
            return configLoader;
        }

        public String getUsername(){
            String prop = properties.getProperty("userName");
            if(prop != null) return prop;
            else throw new RuntimeException("property userName is not specified in the config.properties file");
        }

        public String getBaseUri(){
            String prop = properties.getProperty("BaseUri");
            if(prop != null) return prop;
            else throw new RuntimeException("property BaseUri is not specified in the config.properties file");
        }

        public String getUsersBasePath(){
            String prop = properties.getProperty("usersBasePath");
            if(prop != null) return prop;
            else throw new RuntimeException("property usersBasePath is not specified in the config.properties file");
        }

        public String getPostsBasePath(){
            String prop = properties.getProperty("postsBasePath");
            if(prop != null) return prop;
            else throw new RuntimeException("property postsBasePath is not specified in the config.properties file");
        }

        public String getCommentsBasePath(){
            String prop = properties.getProperty("commentsBasePath");
            if(prop != null) return prop;
            else throw new RuntimeException("property commentsBasePath is not specified in the config.properties file");
        }

        public Integer getSuccessCode(){
            Integer prop = Integer.parseInt(properties.getProperty("successStatusCode"));
            if(prop != null) return prop;
            else throw new RuntimeException("property usersStatusCode is not specified in the config.properties file");
        }

        public Integer getCreateSuccessCode(){
            Integer prop = Integer.parseInt(properties.getProperty("createSuccessCode"));
            if(prop != null) return prop;
            else throw new RuntimeException("property createStatusCode is not specified in the config.properties file");
        }

        public Integer getUpdateSuccessCode(){
            Integer prop = Integer.parseInt(properties.getProperty("updateSuccessCode"));
            if(prop != null) return prop;
            else throw new RuntimeException("property updateSuccessCode is not specified in the config.properties file");
        }

}
