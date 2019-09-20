package com.api.config.properties;

public class LogMessages {

    public final static String buildingsGetAll = "Getting all buildings ";
    public final static String buildingsUpload = "Uploading building \"{}\" with floors \"{}\"";
    public final static String buildingsGetBuilding = "Getting building with name \"{}\"";
    public final static String buildingsUploadFloor = "Uploading floor \"{}\" to building \"{}\"";
    public final static String buildingsDeleteBuilding = "Deleting building \"{}\"";
    public final static String buildingsRenameBuilding = "Renaming building \"{}\" to \"{}\"";
    public final static String buildingsDownloadFloor = "Fetching floor image \"{}\" from building \"{}\"";
    public final static String buildingsDeleteFloor = "Deleting floor \"{}\" in building \"{}\"";
    public final static String buildingsRenameFloor = "Renaming floor \"{}\" to \"{}\" in building \"{}\"";

    public final static String deviceGetAll = "Getting all devices";
    public final static String devicePost = "Saving device \"{}\" with userName \"{}\"";
    public final static String deviceDelete = "Deleting device with id \"{}\"";
    public final static String deviceGetByUser = "Getting all {}'s devices";

    public final static String dummyLogin = "Used loginController";

    public final static String networkGet = "Getting network with id {}";
    public final static String networkGetAll = "Getting all networks";
    public final static String networkEdit = "Updating network with id {}";

    // com.api.component.ScheduledTasks
    public final static String networksAdded = "Added networks: {}";
    public final static String networksFetchFailed = "Fetching networks from provider failed";

    public final static String userDeviceGet = "Getting {}'s location info: {}";

    public final static String uploadDirPath = "Current upload directory path: {}";
    public final static String roomsPost = "Saving room: building: {}, floor: {}, x: {}, y: {}";
    public final static String roomsGetAll = "Getting all rooms";

}
