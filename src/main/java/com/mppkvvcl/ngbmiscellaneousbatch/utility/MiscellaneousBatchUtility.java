package com.mppkvvcl.ngbmiscellaneousbatch.utility;

import com.mppkvvcl.ngbmiscellaneousbatch.enums.SortOrder;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MiscellaneousBatchUtility {

    private static final Logger logger = GlobalResources.getLogger(MiscellaneousBatchUtility.class);

    private static final String[] configuredAgricultureBillMonthCycle = {"APR, OCT"};

    private static final Map<String, List<String>> agricultureBillCycleMappingByStartMonth = new HashMap<>();

    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<String> sortMonths(final List<String> months, final SortOrder sortOrder) {
        final String methodName = "sortMonths() : ";
        logger.info(methodName + "called");
        if (isEmpty(months) || sortOrder == null) {
            logger.error(methodName + "months / date format / sort order is null");
            return months;
        }

        final List<Date> dates = new ArrayList<>();
        //months.parallelStream().forEach(month -> {
        months.forEach(month -> {
            if (!StringUtils.isEmpty(month)) {
                dates.add(getDateFromStringFormat(month, GlobalResources.BILL_MONTH_FORMAT));
            }
        });
        List<Date> sortedDates = dates;
        if (SortOrder.ASC.equals(sortOrder)) {
            sortedDates = dates.stream().sorted().collect(Collectors.toList());
        } else {
            sortedDates = dates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return sortedDates.stream().map(billDate -> getDateInStringFromDate(billDate, GlobalResources.BILL_MONTH_FORMAT).toUpperCase()).collect(Collectors.toList());
    }

    public static Date getDateFromStringFormat(String dateInStringFormat, String dateFormat) {
        final String methodName = "getDateFromStringFormat() : ";
        //logger.info(methodName + "called");
        if (dateInStringFormat == null || dateFormat == null) return null;

        Date dateInDateFormat = null;
        try {
            dateInDateFormat = new SimpleDateFormat(dateFormat).parse(dateInStringFormat);
        } catch (Exception exception) {
            logger.error(methodName + "Exception occurred while parsing date string " + dateInStringFormat, exception.getMessage());
        }
        return dateInDateFormat;
    }

    public static String getDateInStringFromDate(final Date date, final String dateFormat) {
        final String methodName = "getDateInStringFromDate() : ";
        //logger.info(methodName + "called");
        if (date == null || dateFormat == null) return null;

        String dateInStringFormat = null;
        try {
            dateInStringFormat = new SimpleDateFormat(dateFormat).format(date);
        } catch (Exception exception) {
            logger.error(methodName + "Exception occurred while formatting date", exception.getMessage());
        }
        return dateInStringFormat;
    }

    public static String getFinancialYearByDate(final Date date) {
        final String methodName = "getFinancialYearByDate() : ";
        //logger.info(methodName + "called");
        if (date == null) return null;

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        year = (month >= Calendar.APRIL) ? year : year - 1;

        final String financialYear = year + "-" + (year + 1);
        return financialYear;
    }
}
