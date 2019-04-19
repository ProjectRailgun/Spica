package co.bangumi.common.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by roya on 2017/5/27.
 */

public class BangumiDetail extends Bangumi {

    public List<Episode> episodes;

    public BangumiDetail(@NotNull String id, @NotNull String name, @NotNull String name_cn, @NotNull String image, String cover, CoverImage coverImage, @NotNull String coverColor, @NotNull String summary, int air_weekday, @NotNull String air_date, int eps, int type, int status, int favorite_status, int unwatched_count, long update_time, Long bgm_id) {
        super(id, name, name_cn, image, cover, coverImage, coverColor, summary, air_weekday, air_date, eps, type, status, favorite_status, unwatched_count, update_time, bgm_id);
    }
}
