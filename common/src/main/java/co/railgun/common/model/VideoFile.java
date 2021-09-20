package co.railgun.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roya on 2017/5/30.
 */

public class VideoFile {
    public String torrentId;
    public Integer status;
    public String url;
    @SerializedName("file_name")
    public String fileName;
    public Integer resolutionW;
    public Object downloadUrl;
    public String episodeId;
    public String filePath;
    public Integer resolutionH;
    public String bangumiId;
    public Integer duration;
    public Object label;
    public String id;
}
