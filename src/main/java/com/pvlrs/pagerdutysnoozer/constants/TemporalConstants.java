package com.pvlrs.pagerdutysnoozer.constants;

import java.util.concurrent.TimeUnit;

public class TemporalConstants {

    public static final long ONE_MINUTE_IN_MILLIS = 60_000L;

    /* If current user's on-call shift is not fetched, assume that
       the shift ends 8 hours from now (worst case). */
    public static final long DEFAULT_SNOOZE_OFFSET = 8L;

    /* 30-minute margin to be added after current user's shift ends,
       so that the alert is triggered on next on-call's shift. */
    public static final long SNOOZE_MARGIN = 30L;
}
