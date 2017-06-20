//  gfnToday() : 해당 PC의 오늘 날짜를 가져온다. String.
//  gfnTodayTime() : 해당 PC의 오늘 날짜+시간를 가져온다. String.
//  gfnCurrentTime() : 해당 PC의 현재시간를 가져온다. String.
//  gfnAddDate(date, nOffSet) : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
//                              Date Type을 String으로 변환.
//                     argument    : date ('yyyyMMdd' 형태로 표현된 날자)
//                                  nOffSet (날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용됩니다)
//
//  gfnAddMonth(date, nOffSet) : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
//
//  gfnDatetime(nYear, nMonth, nDate) : MiPlatform에서 사용하던 Datetime형식으로 변환
//          nYear (년도), nMonth (월), nDate (일)
//
//
//  gfnGetDiffDay(sFdate, sTdate) : gfnGetDiffDay("20090808", "20091001")
//         sFdate : 시작일자,  sTdate : 종료일자        2개의 날짜간의 Day count.
//
//  gfnDateCheck(sDate) : 날짜에 대한 형식 체크
//          sDate : 검사일자
//
//  gfnGetDay(sDate) : 입력된 날자로부터 요일을 구함
//      sDate  8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
//  요일에 따른 숫자,   0 = 일요일 ~ 6 = 토요일 로 대응됩니다., 오류가 발생할 경우 -1이 Return됩니다.
//
//
//



// 일정 잡기 저장용
        var scheSet = new Set();
        // 생일자 저장용
        var birthSet = new Set();
        // 일정(확정) 저장용
        var scheduleSet = new Set();
        // 일정(미확정) 저장용
        var voteSet = new Set();
        // 전체 일정 저장용
        var groupScheduleSet = new Set();
        var initDay = new Date();
        var cal_date = "";
        /*음력 알고리즘..시작*/
        function gfnRight(strString, nSize) {
            var nStart = String(strString).length;
            var nEnd = Number(nStart) - Number(nSize);
            var rtnVal = strString.substring(nStart, nEnd);
            return rtnVal;
        }

        function gfnIsEmpty(val) {
            var sVal = new String(val);
            val = new String(val);
            if (val == null || val == "null" || sVal.trim().length <= 0 || escape(val) == "undefined") {
                return true;
            }
            else {
                return false;
            }
        }
        /*******************************************************************************
         * Function명 : gfnToday
         * 설명          : 해당 PC의 오늘 날짜를 가져온다.
         * parameter  : None
         * return        : string
         ******************************************************************************/
        function gfnToday() {
            var strToday = "";
            var objDate = new Date();
            var strToday = objDate.getFullYear() + "";
            strToday += gfnRight("0" + (objDate.getMonth() + 1), 2);
            strToday += gfnRight("0" + objDate.getDate(), 2);
            return strToday;
        }
        /*******************************************************************************
         * Function명 : gfnTodayTime
         * 설명       : 해당 PC의 오늘 날짜+시간를 가져온다.
         * parameter : None
         * return         : string
         ******************************************************************************/
        function gfnTodayTime() {
            var strToday = "";
            var objDate = new Date();
            var strToday = objDate.getFullYear() + "";
            strToday += gfnRight("0" + (objDate.getMonth() + 1), 2);
            strToday += gfnRight("0" + objDate.getDate(), 2);
            strToday += gfnRight("0" + objDate.getHours(), 2);
            strToday += gfnRight("0" + objDate.getMinutes(), 2);
            strToday += gfnRight("0" + objDate.getSeconds(), 2);
            return strToday;
        }
        /*******************************************************************************
         * Function명: gfnCurrentTime
         * 설명      : 해당 PC의 현재시간를 가져온다.
         * parameter : None
         * return    : string
         ******************************************************************************/
        function gfnCurrentTime() {
            var strCurrentTime = "";
            var objDate = new Date();
            strCurrentTime += gfnRight("0" + objDate.getHours(), 2);
            strCurrentTime += gfnRight("0" + objDate.getMinutes(), 2);
            strCurrentTime += gfnRight("0" + objDate.getSeconds(), 2);
            return strCurrentTime;
        }
        /**********************************************************************************
         * 함수명      : gfnAddDate
         * 설명        : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
         *               Date Type을 String으로 변환
         * argument    : date ('yyyyMMdd' 형태로 표현된 날자)
         *               nOffSet (날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용됩니다)
         * return Type : String
         * return 내용 : Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
         **********************************************************************************/
        function gfnAddDate(date, nOffSet) {
            var nYear = parseInt(date.substr(0, 4));
            var nMonth = parseInt(date.substr(4, 2));
            var nDate = parseInt(date.substr(6, 2)) + nOffSet;
            return gfnDatetime(nYear, nMonth, nDate);
            //            aHoliday[0] = gfnLunar2Solar( "0" + (nYear-1) + "1230" ) + "설날";
            //            return gfnLunar2Solar( "0" + (nYear) + nMonth + nDate );
        }
        /**********************************************************************************
         * 함수명      : gfnAddMonth
         * 설명        : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
         *               Date Type을 String으로 변환
         * argument    : date ('yyyyMMdd' 형태로 표현된 날자)
         *               nOffSet (날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용됩니다)
         * return Type : String
         * return 내용 : Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
         **********************************************************************************/
        function gfnAddMonth(date, nOffSet) {
            var nYear = parseInt(date.substr(0, 4));
            var nMonth = parseInt(date.substr(4, 2)) + nOffSet;
            var nDate = parseInt(date.substr(6, 2));
            return gfnDatetime(nYear, nMonth, nDate);
        }
        /**********************************************************************************
         * 함수명      : gfnDatetime
         * 설명        : MiPlatform에서 사용하던 Datetime형식으로 변환
         *               Date Type을 String으로 변환
         * argument    : nYear (년도)
         *               nMonth (월)
         *               nDate (일)
         * return Type : String
         * return 내용 : 조합한 날짜를 리턴
         **********************************************************************************/
        function gfnDatetime(nYear, nMonth, nDate) {
            if (nYear.toString().trim().length >= 5) {
                var sDate = new String(nYear);
                var nYear = sDate.substr(0, 4);
                var nMonth = sDate.substr(4, 2);
                var nDate = ((sDate.substr(6, 2) == "") ? 1 : sDate.substr(6, 2));
                var nHours = ((sDate.substr(8, 2) == "") ? 0 : sDate.substr(8, 2));
                var nMinutes = ((sDate.substr(10, 2) == "") ? 0 : sDate.substr(10, 2));
                var nSeconds = ((sDate.substr(12, 2) == "") ? 0 : sDate.substr(12, 2));
                var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(nDate), parseInt(nHours), parseInt(nMinutes), parseInt(nSeconds));
            }
            else {
                var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(((nDate == null) ? 1 : nDate)));
            }
            var strYear = objDate.getYear().toString();
            var strMonth = (objDate.getMonth() + 1).toString();
            var strDate = objDate.getDate().toString();
            if (strMonth.length == 1) strMonth = "0" + strMonth;
            if (strDate.length == 1) strDate = "0" + strDate;
            return strYear + strMonth + strDate;
        }
        /******************************************************************************
         * Function명 : gfnGetDiffDay
         * 사용법 : gfnGetDiffDay("20090808", "20091001")
         * 설명       : 2개의 날짜간의 Day count
         * Params     : sFdate   시작일자
         *              sTdate   종료일자
         * Return     : 양 일자간의 Day count
         ******************************************************************************/
        function gfnGetDiffDay(sFdate, sTdate) {
            sFdate = new String(sFdate);
            sFdate = sFdate.replace(" ", "").replace("-", "").replace("/", "");
            sTdate = new String(sTdate);
            sTdate = sTdate.replace(" ", "").replace("-", "").replace("/", "");
            sFdate = gfnLeft(sFdate, 8);
            sTdate = gfnLeft(sTdate, 8);
            if (sFdate.length != 8 || sTdate.length != 8 || isNumeric(sFdate) == false || isNumeric(sTdate) == false || gfnGetDay(sFdate) == -1 || gfnGetDay(sTdate) == -1) {
                return null;
            }
            var nDiffDate = gfnStrToDate(sTdate) - gfnStrToDate(sFdate);
            var nDay = 1000 * 60 * 60 * 24;
            nDiffDate = parseInt(nDiffDate / nDay);
            if (nDiffDate < 0) {
                nDiffDate = nDiffDate - 1;
            }
            else {
                nDiffDate = nDiffDate + 1;
            }
            return nDiffDate;
        }
        /******************************************************************************
         * Function명 : gfnDateCheck
         * 설명       : 날짜에 대한 형식 체크
         * Params     : sDate   검사일자
         * Return     : 유효성반환 (날짜형식이 아닐경우 FLASE)
         ******************************************************************************/
        function gfnDateCheck(sDate) {
            sDate = sDate.replace(" ", "").replace("-", "").replace("/", "");
            if (isNumeric(sDate) == false || gfnGetDay(sDate) == -1 /*|| datetime(sDate) == datetime("00000101")*/ ) {
                return false;
            }
            return true;
        }
        /******************************************************************************
         * Function명 : gfnGetDay
         * 설명       : 입력된 날자로부터 요일을 구함
         * Params     : sDate  8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
         * Return     : 요일에 따른 숫자.
         *              0 = 일요일 ~ 6 = 토요일 로 대응됩니다.
         *              오류가 발생할 경우 -1이 Return됩니다.
         ******************************************************************************/
        function gfnGetDay(sDate) {
            var objDate = gfnStrToDate(sDate);
            return objDate.getDay();
        }
        /*******************************************************************************
         * 함수명      : gfnStrToDate
         * 설명        : 반자를 전자로 변환하는 함수
         * argument    : sDate 8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
         * Return      : Date
         *******************************************************************************/
        function gfnStrToDate(sDate) {
            var nYear = parseInt(sDate.substr(0, 4));
            var nMonth = parseInt(sDate.substr(4, 2)) - 1;
            var nDate = parseInt(sDate.substr(6, 2));
            return new Date(nYear, nMonth, nDate);
        }
        /******************************************************************************
         * 2010.05.11. 권세준 추가 시작
         ******************************************************************************/
        /******************************************************************************
         * Function명 : gfnGetDayName
         * 설명       : 입력된 날자로부터 요일명을 구함
         * Params     : sDate  8자리 형식으로된 날짜. yyyyMMdd의 형식으로 입력됩니다.
         * Return     : 요일명
         ******************************************************************************/
        function gfnGetDayName(sDate) {
            var objDayName = new Array("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일");
            var objDate = gfnGetDay(sDate);
            return objDayName[objDate];
        }
        /******************************************************************************
         * Function명 : gfnIsLeapYear
         * 설명       : 윤년여부 확인
         * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
         * Return     : 
         *               - sDate가 윤년인 경우 = true
         *     - sDate가 윤년이 아닌 경우 = false
         *       - sDate가 입력되지 않은 경우 = false
         ******************************************************************************/
        function gfnIsLeapYear(sDate) {
            var ret;
            var nY;
            if (gfnIsEmpty(sDate)) return false;
            nY = parseInt(sDate.substring(0, 4), 10);
            if ((nY % 4) == 0) {
                if ((nY % 100) != 0 || (nY % 400) == 0) ret = true;
                else ret = false;
            }
            else ret = false;
            return ret;
        }
        /******************************************************************************
         * Function명 : gfnLastDateNum
         * 설명       : 해당월의 마지막 날짜를 숫자로 구하기
         * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
         * Return     : 
         *               - 성공 = 마지막 날짜 숫자값 ( 예 : 30 )
         *     - 실패 = -1
         ******************************************************************************/
        function gfnLastDateNum(sDate) {
            var nMonth, nLastDate;
            if (gfnIsEmpty(sDate)) return -1;
            nMonth = parseInt(sDate.substr(4, 2), 10);
            if (nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7 || nMonth == 8 || nMonth == 10 || nMonth == 12) nLastDate = 31;
            else if (nMonth == 2) {
                if (gfnIsLeapYear(sDate) == true) nLastDate = 29;
                else nLastDate = 28;
            }
            else nLastDate = 30;
            return nLastDate;
        }
        /******************************************************************************
         * Function명 : gfnLastDate
         * 설명       : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기 
         * Params     : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
         * Return     : 
         *               - 성공 = yyyyMMdd형태의 마지막 날짜 ( 예 : "20121130" )
         *     - 실패 = ""
         ******************************************************************************/
        function gfnLastDate(sDate) {
            if (gfnIsEmpty(sDate)) return "";
            var nLastDate = gfnLastDateNum(sDate);
            return sDate.substr(0, 6) + nLastDate.toString();
        }
        /******************************************************************************
         * Function명 : gfnSolar2Lunar
         * 설명       : 양력을 음력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
         * Params     : sDate : yyyyMMdd형태의 양력일자 ( 예 : "20121122" )
         * Return     : return값이 8자리가 아니고 9자리임에 주의
         *               - 성공 = Flag(1 Byte) + (yyyyMMdd형태의 음력일자)
         *        ( Flag : 평달 = "0", 윤달 = "1" )
         *       - 실패 = "" ( 1841 ~ 2043 범위 오류시 )
         ******************************************************************************/
        function gfnSolar2Lunar(sDate) {
            var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
            var aMd = new Array();
            var aBaseInfo = new Array();
            var aDt = new Array(); // 매년의 음력일수를 저장할 배열 변수
            var td; // 음력일을 계산하기 위해 양력일과의 차이를 저장할 변수
            var td1; // 1840년까지의 날수
            var td2; // 현재까지의 날수
            var mm; // 임시변수
            var nLy, nLm, nLd; // 계산된 음력 년, 월, 일을 저장할 변수
            var sLyoon; // 현재월이 윤달임을 표시
            if (gfnIsEmpty(sDate)) return "";
            sY = parseInt(sDate.substr(0, 4), 10);
            sM = parseInt(sDate.substr(4, 2), 10);
            sD = parseInt(sDate.substr(6, 2), 10);
            if (sY < 1841 || sY > 2043) return "";
            aBaseInfo = _SolarBase();
            aMd = sMd.split(",");
            if (gfnIsLeapYear(sDate) == true) aMd[1] = 29;
            else aMd[1] = 28;
            td1 = 672069; // 672069 = 1840 * 365 + 1840/4 - 1840/100 + 1840/400 + 23  //1840년까지 날수
            // 1841년부터 작년까지의 날수
            td2 = (sY - 1) * 365 + parseInt((sY - 1) / 4) - parseInt((sY - 1) / 100) + parseInt((sY - 1) / 400);
            // 전월까지의 날수를 더함
            for (i = 0; i <= sM - 2; i++) td2 = td2 + parseInt(aMd[i]);
            // 현재일까지의 날수를 더함
            td2 = td2 + sD;
            // 양력현재일과 음력 1840년까지의 날수의 차이
            td = td2 - td1 + 1;
            // 1841년부터 음력날수를 계산
            for (i = 0; i <= sY - 1841; i++) {
                aDt[i] = 0;
                for (j = 0; j <= 11; j++) {
                    switch (parseInt(aBaseInfo[i * 12 + j])) {
                    case 1:
                        mm = 29;
                        break;
                    case 2:
                        mm = 30;
                        break;
                    case 3:
                        mm = 58; // 29 + 29
                        break;
                    case 4:
                        mm = 59; // 29 + 30
                        break;
                    case 5:
                        mm = 59; // 30 + 29
                        break;
                    case 6:
                        mm = 60; // 30 + 30
                        break;
                    }
                    aDt[i] = aDt[i] + mm;
                }
            }
            // 1840년 이후의 년도를 계산 - 현재까지의 일수에서 위에서 계산된 1841년부터의 매년 음력일수를 빼가면수 년도를 계산
            nLy = 0;
            do {
                td = td - aDt[nLy];
                nLy = nLy + 1;
            }
            while (td > aDt[nLy]);
            nLm = 0;
            sLyoon = "0"; // 현재월이 윤달임을 표시할 변수 - 기본값 평달
            do {
                if (parseInt(aBaseInfo[nLy * 12 + nLm]) <= 2) {
                    mm = parseInt(aBaseInfo[nLy * 12 + nLm]) + 28;
                    if (td > mm) {
                        td = td - mm;
                        nLm = nLm + 1;
                    }
                    else break;
                }
                else {
                    switch (parseInt(aBaseInfo[nLy * 12 + nLm])) {
                    case 3:
                        m1 = 29;
                        m2 = 29;
                        break;
                    case 4:
                        m1 = 29;
                        m2 = 30;
                        break;
                    case 5:
                        m1 = 30;
                        m2 = 29;
                        break;
                    case 6:
                        m1 = 30;
                        m2 = 30;
                        break;
                    }
                    if (td > m1) {
                        td = td - m1;
                        if (td > m2) {
                            td = td - m2;
                            nLm = nLm + 1;
                        }
                        else {
                            sLyoon = "1";
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            while (1);
            nLy = nLy + 1841;
            nLm = nLm + 1;
            nLd = td;
            return sLyoon + nLy + gfnRight("0" + nLm, 2) + gfnRight("0" + nLd, 2);
        }
        /******************************************************************************
         * Function명 : gfnLunar2Solar
         * 설명       : 음력을 양력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
         * Params     : sDate : Flag(1 Byte)+yyyyMMdd형태의 음력일자 ( 예 : "020121122" ) ( Flag : 평달 = "0", 윤달 = "1" )
         * Return     : 
         *               - 성공 = yyyyMMdd형태의 양력일자
         *        ( Flag : 평달 = "0", 윤달 = "1" )
         *       - 실패 = null 
         *           - 1841 ~ 2043 범위 오류의 경우
         *           - sDate가 9자리가 아닐경우
         *           - sDate의 첫자리 Flag가 "0"도 아니고 "1"도 아닌 경우
         ******************************************************************************/
        function gfnLunar2Solar(sDate) {
            var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
            var aMd = new Array();
            var aBaseInfo = new Array();
            var nLy, nLm, nLd, sLflag; // 전해온 음력 인자값을 저장할 년, 월, 일, 윤달여부 임시변수
            var nSy, nSm, nSd; // 계산된 양력 년, 월, 일을 저장할 변수
            var y1, m1, i, j, y2, y3; // 임시변수 
            var leap;
            if (gfnIsEmpty(sDate)) return "";
            if (sDate.length != 9) return "";
            sLflag = sDate.substr(0, 1);
            nLy = parseInt(sDate.substr(1, 4), 10);
            nLm = parseInt(sDate.substr(5, 2), 10);
            nLd = parseInt(sDate.substr(7, 2), 10);
            if (nLy < 1841 || nLy > 2043) return "";
            if (sLflag != "0" && sLflag != "1") return "";
            aBaseInfo = _SolarBase();
            aMd = sMd.split(",");
            if (gfnIsLeapYear(sDate.substr(1, 8)) == true) aMd[1] = 29;
            else aMd[1] = 28;
            y1 = nLy - 1841;
            m1 = nLm - 1;
            leap = 0;
            if (parseInt(aBaseInfo[y1 * 12 + m1]) > 2) leap = gfnIsLeapYear(nLy + "0101");
            if (leap == 1) {
                switch (parseInt(aBaseInfo[y1 * 12 + m1])) {
                case 3:
                    mm = 29;
                    break;
                case 4:
                    mm = 30;
                    break;
                case 5:
                    mm = 29;
                    break;
                case 6:
                    mm = 30;
                    break;
                }
            }
            else {
                switch (parseInt(aBaseInfo[y1 * 12 + m1])) {
                case 1:
                    mm = 29;
                    break;
                case 2:
                    mm = 30;
                    break;
                case 3:
                    mm = 29;
                    break;
                case 4:
                    mm = 29;
                    break;
                case 5:
                    mm = 30;
                    break;
                case 6:
                    mm = 30;
                    break;
                }
            }
            td = 0;
            for (i = 0; i <= y1 - 1; i++) {
                for (j = 0; j <= 11; j++) {
                    switch (parseInt(aBaseInfo[i * 12 + j])) {
                    case 1:
                        td = td + 29;
                        break;
                    case 2:
                        td = td + 30;
                        break;
                    case 3:
                        td = td + 58;
                        break;
                    case 4:
                        td = td + 59;
                        break;
                    case 5:
                        td = td + 59;
                        break;
                    case 6:
                        td = td + 60;
                        break;
                    }
                }
            }
            for (j = 0; j <= m1 - 1; j++) {
                switch (parseInt(aBaseInfo[y1 * 12 + j])) {
                case 1:
                    td = td + 29;
                    break;
                case 2:
                    td = td + 30;
                    break;
                case 3:
                    td = td + 58;
                    break;
                case 4:
                    td = td + 59;
                    break;
                case 5:
                    td = td + 59;
                    break;
                case 6:
                    td = td + 60;
                    break;
                }
            }
            if (leap == 1) {
                switch (parseInt(aBaseInfo[y1 * 12 + m1])) {
                case 3:
                    mm = 29;
                    break;
                case 4:
                    mm = 29;
                    break;
                case 5:
                    mm = 30;
                    break;
                case 6:
                    mm = 30;
                    break;
                }
            }
            td = td + nLd + 22;
            if (sLflag == "1") {
                switch (parseInt(aBaseInfo[y1 * 12 + m1])) {
                case 3:
                    td = td + 29;
                    break;
                case 4:
                    td = td + 30;
                    break;
                case 5:
                    td = td + 29;
                    break;
                case 6:
                    td = td + 30;
                    break;
                }
            }
            y1 = 1840;
            do {
                y1 = y1 + 1;
                leap = gfnIsLeapYear(y1 + "0101");
                if (leap == 1) y2 = 366;
                else y2 = 365;
                if (td <= y2) break;
                td = td - y2;
            }
            while (1);
            nSy = y1;
            aMd[1] = y2 - 337;
            m1 = 0;
            do {
                m1 = m1 + 1;
                if (td <= parseInt(aMd[m1 - 1])) break;
                td = td - parseInt(aMd[m1 - 1]);
            }
            while (1);
            nSm = m1;
            nSd = td;
            y3 = nSy;
            td = y3 * 365 + parseInt(y3 / 4) - parseInt(y3 / 100) + parseInt(y3 / 400);
            for (i = 0; i <= nSm - 1; i++) td = td + parseInt(aMd[i]);
            td = td + nSd;
            return y3 + gfnRight("0" + nSm, 2) + gfnRight("0" + nSd, 2);
        }
        /******************************************************************************
         * Function명 : gfnGetHolidays
         * 설명       : 양력 nYear에 해당하는 년도의 법정 공휴일(양력) List 모두 구하기
         * Params     : nYear : nYear에 해당하는 년도 ( 예 : 2012 )
         * Return     : 
         *               - 성공 = 공휴일 List Array ==> 각 Array[i]="yyyyMMdd공휴일명" 으로 return된다.
         *        ( 예 : Array[0] = "20120101신정" )
         *       - 실패 = 빈 Array
         ******************************************************************************/
        function gfnGetHolidays(nYear) {
            var nYear;
            var aHoliday = new Array();
            if (gfnIsEmpty(nYear)) return aHoliday;
            /////// 음력 체크
            // 구정
            //         aHoliday[0] = gfnLunar2Solar( "0" + (nYear-1) + "1230" ) + "설날";
            ////        console.log(aHoliday[0].substr(0,4));
            ////        console.log(aHoliday[0].substr(4, 2));
            ////        console.log(aHoliday[0].substr(6, 2));
            ////         aHoliday[1] = gfnAddDate(aHoliday[0], 1) + "설날";
            //         aHoliday[1] = gfnLunar2Solar( "0" + (nYear-1) + "1232" ) + "설날";
            ////         aHoliday[2] = gfnAddDate(aHoliday[1], 1) + "설날";
            //         aHoliday[2] = gfnLunar2Solar( "0" + (nYear-1) + "1231" ) + "설날";
            // 석가탄신일
            aHoliday[0] = gfnLunar2Solar("0" + nYear + "0408") + "석가탄신일";
            // 추석
            //         aHoliday[4] = gfnLunar2Solar( "0" + nYear + "0814" ) + "추석";
            ////         aHoliday[5] = gfnAddDate(aHoliday[4], 1) + "추석";
            //         aHoliday[5] = gfnLunar2Solar( "0" + nYear + "0816" ) + "추석";
            ////         aHoliday[6] = gfnAddDate(aHoliday[5], 1) + "추석"; 
            //         aHoliday[6] = gfnLunar2Solar( "0" + nYear + "0815" ) + "추석";
            //         /////// 양력 체크
            aHoliday[1] = nYear + "0101" + "신정";
            aHoliday[2] = nYear + "0301" + "삼일절";
            aHoliday[3] = nYear + "0505" + "어린이날";
            aHoliday[4] = nYear + "0606" + "현충일";
            aHoliday[5] = nYear + "0815" + "광복절";
            aHoliday[6] = nYear + "1225" + "성탄절";
            //        var nYear  = parseInt(date.substr(0, 4));
            //            var nMonth = parseInt(date.substr(4, 2));
            //            var nDate  = parseInt(date.substr(6, 2)) + nOffSet;
            //            return gfnDatetime(nYear, nMonth, nDate);
            return aHoliday.sort();
        }
        /******************************************************************************
         * Function명 : _SolarBase
         * 설명       : 각 월별 음력 기준 정보를 처리하는 함수(처리가능 기간  1841 - 2043년) 단, 내부에서 사용하는 함수임
         * Params     :  없음
         * Return     : 
         *               - 성공 = 음력 기준정보
         ******************************************************************************/
        function _SolarBase() {
            var kk;
            //1841
            kk = "1,2,4,1,1,2,1,2,1,2,2,1,";
            kk += "2,2,1,2,1,1,2,1,2,1,2,1,";
            kk += "2,2,2,1,2,1,4,1,2,1,2,1,";
            kk += "2,2,1,2,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
            kk += "2,1,2,1,5,2,1,2,2,1,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,2,3,2,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
            //1851
            kk += "2,2,1,2,1,1,2,1,2,1,5,2,";
            kk += "2,1,2,2,1,1,2,1,2,1,1,2,";
            kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,1,2,5,2,1,2,1,2,";
            kk += "1,1,2,1,2,2,1,2,2,1,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,5,2,1,2,1,2,2,2,";
            kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
            kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
            kk += "2,1,6,1,1,2,1,1,2,1,2,2,";
            //1861
            kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
            kk += "2,1,2,1,2,2,1,2,2,3,1,2,";
            kk += "1,2,2,1,2,1,2,2,1,2,1,2,";
            kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,1,2,4,1,2,2,1,2,2,1,";
            kk += "2,1,1,2,1,1,2,2,1,2,2,2,";
            kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
            kk += "1,2,2,3,2,1,1,2,1,2,2,1,";
            kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
            kk += "2,2,2,1,2,1,2,1,1,5,2,1,";
            //1871
            kk += "2,2,1,2,2,1,2,1,2,1,1,2,";
            kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
            kk += "1,1,2,1,2,4,2,1,2,2,1,2,";
            kk += "1,1,2,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
            kk += "2,2,1,1,5,1,2,1,2,2,1,2,";
            kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
            kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
            kk += "2,2,4,2,1,2,1,1,2,1,2,1,";
            kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
            //1881
            kk += "1,2,1,2,1,2,5,2,2,1,2,1,";
            kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
            kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
            kk += "2,1,1,2,3,2,1,2,2,1,2,2,";
            kk += "2,1,1,2,1,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
            kk += "2,2,1,5,2,1,1,2,1,2,1,2,";
            kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
            kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
            kk += "1,5,2,1,2,2,1,2,1,2,1,2,";
            //1891
            kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
            kk += "1,1,2,1,1,5,2,2,1,2,2,2,";
            kk += "1,1,2,1,1,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,5,1,2,1,2,1,2,1,";
            kk += "2,2,2,1,2,1,1,2,1,2,1,2,";
            kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
            kk += "2,1,5,2,2,1,2,1,2,1,2,1,";
            kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
            kk += "1,2,1,1,2,1,2,5,2,2,1,2,";
            //1901
            kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,3,2,1,1,2,2,1,2,";
            kk += "2,2,1,2,1,1,2,1,1,2,2,1,";
            kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
            kk += "1,2,2,4,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
            kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
            kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
            //1911
            kk += "2,1,2,1,1,5,1,2,2,1,2,2,";
            kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
            kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
            kk += "2,2,1,2,5,1,2,1,2,1,1,2,";
            kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
            kk += "2,3,2,1,2,2,1,2,2,1,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,5,2,2,1,2,2,";
            kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
            //1921
            kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
            kk += "2,1,2,2,3,2,1,1,2,1,2,2,";
            kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
            kk += "2,1,2,1,2,2,1,2,1,2,1,1,";
            kk += "2,1,2,5,2,1,2,2,1,2,1,2,";
            kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
            kk += "1,5,1,2,1,1,2,2,1,2,2,2,";
            kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
            kk += "1,2,2,1,1,5,1,2,1,2,2,1,";
            //1931
            kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
            kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
            kk += "1,2,2,1,6,1,2,1,2,1,1,2,";
            kk += "1,2,1,2,2,1,2,2,1,2,1,2,";
            kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,4,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
            kk += "2,2,1,1,2,1,4,1,2,2,1,2,";
            kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
            kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
            //1941
            kk += "2,2,1,2,2,4,1,1,2,1,2,1,";
            kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
            kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
            kk += "1,1,2,4,1,2,1,2,2,1,2,2,";
            kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
            kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
            kk += "2,5,1,2,1,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
            kk += "2,2,1,2,1,2,3,2,1,2,1,2,";
            kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
            //1951
            kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,4,2,1,2,1,2,1,2,";
            kk += "1,2,1,1,2,2,1,2,2,1,2,2,";
            kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
            kk += "2,1,4,1,1,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,2,1,1,5,2,1,2,2,";
            kk += "1,2,2,1,2,1,1,2,1,2,1,2,";
            kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
            kk += "2,1,2,1,2,5,2,1,2,1,2,1,";
            //1961
            kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
            kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,2,3,2,1,2,1,2,2,2,1,";
            kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,1,1,2,1,1,2,2,1,";
            kk += "2,2,5,2,1,1,2,1,1,2,2,1,";
            kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
            kk += "1,2,2,1,2,1,5,2,1,2,1,2,";
            kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
            kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
            //1971
            kk += "1,2,1,1,5,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,2,1,1,2,1,1,2,2,2,1,";
            kk += "2,2,1,5,1,2,1,1,2,2,1,2,";
            kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
            kk += "2,2,1,2,1,2,1,5,2,1,1,2,";
            kk += "2,1,2,2,1,2,1,2,1,2,1,1,";
            kk += "2,2,1,2,1,2,2,1,2,1,2,1,";
            kk += "2,1,1,2,1,6,1,2,2,1,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
            //1981
            kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
            kk += "2,1,2,3,2,1,1,2,2,1,2,2,";
            kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
            kk += "2,1,2,2,1,1,2,1,1,5,2,2,";
            kk += "1,2,2,1,2,1,2,1,1,2,1,2,";
            kk += "1,2,2,1,2,2,1,2,1,2,1,1,";
            kk += "2,1,2,2,1,5,2,2,1,2,1,2,";
            kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
            kk += "1,2,1,1,5,1,2,1,2,2,2,2,";
            //1991
            kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
            kk += "1,2,2,1,1,2,1,1,2,1,2,2,";
            kk += "1,2,5,2,1,2,1,1,2,1,2,1,";
            kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
            kk += "1,2,2,1,2,2,1,5,2,1,1,2,";
            kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
            kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,1,2,3,2,2,1,2,2,2,1,";
            kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
            kk += "2,2,1,1,2,1,1,2,1,2,2,1,";
            //2001
            kk += "2,2,2,3,2,1,1,2,1,2,1,2,";
            kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
            kk += "2,2,1,2,2,1,2,1,1,2,1,2,";
            kk += "1,5,2,2,1,2,1,2,2,1,1,2,";
            kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
            kk += "1,1,2,1,2,1,5,2,2,1,2,2,";
            kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
            kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
            kk += "2,2,1,1,5,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
            //2011
            kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
            kk += "2,1,6,2,1,2,1,1,2,1,2,1,";
            kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
            kk += "1,2,1,2,1,2,1,2,5,2,1,2,";
            kk += "1,2,1,1,2,1,2,2,2,1,2,2,";
            kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
            kk += "2,1,1,2,3,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
            kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
            kk += "2,1,2,5,2,1,1,2,1,2,1,2,";
            //2021
            kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
            kk += "2,1,2,1,2,2,1,2,1,2,1,2,";
            kk += "1,5,2,1,2,1,2,2,1,2,1,2,";
            kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
            kk += "2,1,2,1,1,5,2,1,2,2,2,1,";
            kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
            kk += "1,2,1,2,1,1,2,1,1,2,2,2,";
            kk += "1,2,2,1,5,1,2,1,1,2,2,1,";
            kk += "2,2,1,2,2,1,1,2,1,1,2,2,";
            kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
            //2031
            kk += "2,1,5,2,1,2,2,1,2,1,2,1,";
            kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
            kk += "1,2,1,1,2,1,5,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
            kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
            kk += "2,2,1,2,1,4,1,1,2,1,2,2,";
            kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
            kk += "2,2,1,2,1,2,1,2,1,1,2,1,";
            kk += "2,2,1,2,5,2,1,2,1,2,1,1,";
            kk += "2,1,2,2,1,2,2,1,2,1,2,1,";
            //2041
            kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
            kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
            kk += "1,2,1,1,2,1,1,2,2,1,2,2";
            var arr = new Array();
            arr = kk.split(",");
            return arr;
        }
        //현재월에서 +-월의 마지막날을 찾는 function
        function gfnNowLastDay(intNum) {
            var objDate2 = new Date();
            objDate2.addMonth(intNum + 1);
            objDate2.setDate(1);
            objDate2.addDate(-1);
            var last_date = objDate2.toFormatString("%Y%m%d");
            return last_date;
        }
        //현재월에서 +-월의 첫날을 찾는 function
        function gfnNowFirstDay(intNum) {
            var objDate2 = new Date();
            objDate2.addMonth(intNum);
            objDate2.setDate(1);
            objDate2.addDate(0);
            var first_date = objDate2.toFormatString("%Y%m%d");
            return first_date;
        }
        /*******************************************************************************
         * Function명 : gfnTodayDiv
         * 설명          : 해당 PC의 오늘 날짜를 가져온다. (년/월/일 사이에 구분자를 원하는 형태로)
         * parameter  : strDel   구분자
         * return        : string
         ******************************************************************************/
        function gfnTodayDiv(strDel) {
            var strToday = "";
            var objDate = new Date();
            var strToday = objDate.getFullYear();
            strToday = strToday + strDel + gfnRight("0" + (objDate.getMonth() + 1), 2);
            strToday = strToday + strDel + gfnRight("0" + objDate.getDate(), 2);
            return strToday;
        }
        /*음력 알고리즘..끝*/
        var today = new Date(); // 오늘 날짜
        $(document).ready(function () {
            /*
                드레그앤 드롭
            */
            /////////////////////////////    
            // console.log("getGroupInfo");
            getGroupInfo();
            // console.log("getGroupBirth");
            getGroupBrith();
            // console.log("cal");
            //            $('.saturday').css('color','blue');
            $('#yearSelect').change(function () {
                initToday();
                //                console.log(parseInt($("#yearSelect").val()));            
                today = new Date(parseInt($("#yearSelect").val()), today.getMonth(), today.getDate());
                //                console.log(today);   
                //                initToday();
                buildCalendar();
                //                console.log($(this).val());
                if ($(this).val() == "2030" && $("#monthSelect").val() == "12") {
                    $("#nextButton").css("display", "none");
                }
                else if ($(this).val() == "2017" && $("#monthSelect").val() == "1") {
                    $("#preButton").css("display", "none");
                }
                else {
                    $("#preButton").css("display", "block");
                    $("#nextButton").css("display", "block");
                }
            }); //연도 선택
            $('#monthSelect').change(function () {
                initToday();
                //                console.log(parseInt($("#monthSelect").val()));            
                today = new Date(parseInt($("#yearSelect").val()), parseInt($("#monthSelect").val()) - 1, today.getDate());
                //                console.log(today); 
                //                initToday();
                buildCalendar();
                if ($("#yearSelect").val() == "2030" && $(this).val() == "12") {
                    $("#nextButton").css("display", "none");
                }
                else if ($("#yearSelect").val() == "2017" && $(this).val() == "1") {
                    $("#preButton").css("display", "none");
                }
                else {
                    $("#preButton").css("display", "block");
                    $("#nextButton").css("display", "block");
                }
            }); // 달 선택
            $('#yearSelect').val(today.getFullYear()).attr("selected", "selected");
            $('#monthSelect').val(today.getMonth() + 1).attr("selected", "selected");
            //            initToday(); //  주석1
        });

        function update() {
            $.ajax({
                type: "POST"
                , url: "json/holiday.json"
                , dataType: "JSON"
                , success: function (data) {
                    var dateArr = new Array();
                    var titleArr = new Array();
                    $.each(data, function (index, item) {
                        dateArr.push(item.날짜);
                        titleArr.push(item.이벤트);
                    });
                    buildCalendar(dateArr, titleArr);
                    initToday();
                }
                , error: function (req) {
                    //                    alert('통신실패');
                    alert("상태 : " + req.status + ", " + req.responseText + ", error : " + req.error);
                }
            });
        }

        function dateToFormat(sendCal) {
            var dateStr = "";
            if ((sendCal.getMonth() + 1) / 10 < 1) {
                if (sendCal.getDate() / 10 < 1) {
                    dateStr = sendCal.getFullYear() + "0" + (sendCal.getMonth() + 1) + "0" + sendCal.getDate();
                }
                else {
                    dateStr = sendCal.getFullYear() + "0" + (sendCal.getMonth() + 1) + "" + sendCal.getDate();
                }
            }
            else {
                if (sendCal.getDate() / 10 < 1) {
                    dateStr = sendCal.getFullYear() + "" + (sendCal.getMonth() + 1) + "0" + sendCal.getDate();
                }
                else {
                    dateStr = sendCal.getFullYear() + "" + (sendCal.getMonth() + 1) + "" + sendCal.getDate();
                }
            }
            return dateStr;
        }

        function initToday() {
            //            console.log(dateToFormat(initDay));
            //			console.log("!");
            $('tr').children().each(function (i) {
                if ($(this).text() == initDay.getDate() && $('#yearSelect option:selected').val() == initDay.getFullYear() && $('#monthSelect option:selected').val() == initDay.getMonth() + 1) {
                    $(this).css("background-color", "yellow");
                }
                for (let item of scheSet.keys()) {
                    if (item == dateToFormat(initDay)) {
                        if ($('#yearSelect option:selected').val() == initDay.getFullYear() && $('#monthSelect option:selected').val() == initDay.getMonth() + 1) {
                            $('#date' + initDay.getDate()).css("backgroundColor", "green");
                            //                            console.log(initDay.getDate());
                        }
                    }
                }
            });
            voteMeeting(cal_date);
        }

        function prevCalendar() {
            // 이전 달을 today에 값을 저장하고 달력을 뿌려줍니다.
            today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
            $('#yearSelect').val(today.getFullYear()).attr("selected", "selected");
            $('#monthSelect').val(today.getMonth() + 1).attr("selected", "selected");
            if ($("#yearSelect").val() == "2017" && $("#monthSelect").val() == "1") {
                $("#preButton").css("display", "none");
            }
            else {
                $("#nextButton").css("display", "block");
            }
            update();
        }

        function nextCalendar() {
            // 다음 달을 today에 값을 저장하고 달력을 뿌려줍니다.
            today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
            //            console.log(today);
            $('#yearSelect').val(today.getFullYear()).attr("selected", "selected");
            $('#monthSelect').val(today.getMonth() + 1).attr("selected", "selected");
            if ($("#yearSelect").val() == "2030" && $("#monthSelect").val() == "12") {
                $("#nextButton").css("display", "none");
            }
            else {
                $("#preButton").css("display", "block");
            }
            update();
        }
        //        addSche = "";
        //        scheArr = new Array(32);
        ////        scheArr[0] = "";
        //        for(var s = 0; s < 32; s++ ){
        //            scheArr[s] = false;
        //        }
        //        
        //        var scheSet = new Set();
        function getDayInfo(a) { // 선택한 날짜 정보를 얻기 위한 함수
            if (a != null) {
                var yearInfo = $("#yearSelect").val() + "";
                var monthInfo = $("#monthSelect").val() + "";
                var dayInfo = $(a).attr("id").substr(4, $(a).attr("id").length - 4) + "";
                if (monthInfo.length == 1) {
                    monthInfo = "0" + monthInfo;
                }
                if (dayInfo.length == 1) {
                    dayInfo = "0" + dayInfo;
                }
                //                scheArr[0] = yearInfo + monthInfo;
                var selectDate = yearInfo + monthInfo + dayInfo;

//                console.log($(a).css("backgroundColor"));
                if($(a).css("backgroundColor") == "rgb(255, 0, 0)"){
                    alert("현재 투표중인 날짜입니다!");
                    return;                    
                }else if($(a).css("backgroundColor") == "rgb(255, 128, 128)"){
                    alert("이미 확정된 날짜 입니다!");
                    return;   
                }
                
                
               
                if (selectDate < dateToFormat(initDay)) {
                    alert("지난 날짜는 선택할수 없습니다!")
                    return;
                }
                else {
                    if ($(a).css("backgroundColor") == "rgb(98, 188, 250)") {
                        $(a).css("backgroundColor", "white");
                        //                    scheArr[parseInt(dayInfo)] = false;
                        scheSet.delete(yearInfo + monthInfo + dayInfo);
                        for (let item of voteSet.keys()) {
                            if (item.split("-")[0] == (yearInfo + monthInfo + dayInfo)) {
                                //                            console.log(yearInfo + monthInfo + dayInfo);
                                $(a).css("backgroundColor", "#ff8080");
                            }
                        }
                        for (let item of voteSet.keys()) {
                            if (item.split("-")[0] == (yearInfo + monthInfo + dayInfo)) {
                                $(a).css("backgroundColor", "#00ff80");
                            }
                        }
                        // 생일표시
                        for (let item of birthSet.keys()) {
                            if (item.split("-")[0].substr(2, 4) == (monthInfo + dayInfo)) {
                                $(a).css("backgroundColor", "#0080ff");
                                console.log(monthInfo + dayInfo);
                            }
                        }
                        initToday();
                    }
                    else {
                        $(a).css("backgroundColor", "#62bcfa");
                        //                    scheArr[parseInt(dayInfo)] = dayInfo;
                        scheSet.add(yearInfo + monthInfo + dayInfo);
                        console.log(selectDate);
                    }
                }
                //                addSche += yearInfo + monthInfo + dayInfo + ",";
            }
        }
        //스케줄 추가 함수
        function addSchedule() {
//            console.log(scheSet.size);
            if(scheSet.size == 0){
                alert("일정이 선택 되지 않았습니다.");
                return;
            }
            
            $("#modal").css("display", "block");
            $("#titleAdd").css("display", "block");
            $("#answerBox").css("display", "none");
            
            var a = "";
            //            console.log(scheSet);
            for (let item of scheSet.keys()) console.log(item);
//            scheSet = new Set();
        }
        

// ================ 요넘이 캘린더 그려주는 애. ================ 
        function buildCalendar(dateArr, titleArr, memChoiceArr) {
            var nMonth = new Date(today.getFullYear(), today.getMonth(), 1); // 이번 달의 첫째 날
            var lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0); // 이번 달의 마지막 날
            var tblCalendar = document.getElementById("calendar"); // 테이블 달력을 만들 테이블
            var tblCalendarYM = document.getElementById("calendarYM"); // yyyy년 m월 출력할 곳
            var dateParam = "";
            var dateColor = new Date();
            var holiday = gfnGetHolidays(today.getFullYear());
            var t = 0;
            var countHoliday = 0;
            //            var testTime = new Date(2017,04,01).getTime() - new Date(2017,03,31).getTime();
            //            
            //            console.log(new Date(2017,3,1).getTime());
            //            console.log(new Date(2017,2,31).getTime());
            ////            
            //            for(var k = 0; k<holiday.length; k++){
            //                console.log(holiday[k]);
            //                
            //            }
            while (tblCalendar.rows.length > 2) {
                tblCalendar.deleteRow(tblCalendar.rows.length - 1);
            }
            var row = null;
            row = tblCalendar.insertRow();
            var cnt = 0;
            // 1일이 시작되는 칸을 맞추어 줌
            for (i = 0; i < nMonth.getDay(); i++) {
                cell = row.insertCell();
                cnt = cnt + 1;
            }
            
            // =====================    달력 출력하는 코드!    ==============
            for (calDay = 1; calDay <= lastDate.getDate(); calDay++) { // 1일부터 마지막 일까지
                cell = row.insertCell();
                $(cell).attr("id", "date" + calDay);
                $(cell).attr("onclick", "getDayInfo(this)");
                $(cell).attr("style", "cursor:pointer");
                initToday();
                if ((today.getMonth() + 1) / 10 < 1) { // 월처리
                    if (calDay / 10 < 1) {
                        dateParam = today.getFullYear() + "0" + (today.getMonth() + 1) + "0" + calDay;
                    }  // dateParam은 원래 2017712에서  7앞에 0을 넣는다. 20170712
                    else {
                        dateParam = today.getFullYear() + "0" + (today.getMonth() + 1) + "" + calDay;
                    }  
                }
                else {
                    if (calDay / 10 < 1) { // 일처리
                        dateParam = today.getFullYear() + "" + (today.getMonth() + 1) + "0" + calDay;
                    }
                    else {
                        dateParam = today.getFullYear() + "" + (today.getMonth() + 1) + "" + calDay;
                    }
                }
                
                for (let item of scheSet.keys()) {
                    if (item == dateParam) {
                        $('#date' + calDay).css("backgroundColor", "#62bcfa");   // 선택했을때 초록색으로 바꿔주는 부분!
                    }
                } //선택한 날짜 초록색으로 표기
                
                var saveSolar = gfnSolar2Lunar(dateParam);
                dateColor.setFullYear(today.getFullYear());
                dateColor.setMonth(today.getMonth());
                dateColor.setDate(calDay);
                
                if (dateColor.getDay() == 6) {
                    cell.innerHTML += "<div class='saturday' style='color:blue;'>" + calDay + "</div>";
                    //                    $('.saturday').css('color','blue');
                }
                else if (dateColor.getDay() == 0) {
                    cell.innerHTML += "<div class='sunday'>" + calDay + "</div>";
                    $('.sunday').css('color', 'red');
                }
                else {
                    cell.innerHTML += "<div>" + calDay + "</div>";
                }
                if (calDay % 9 == 0) cell.innerHTML += "<div style='font-size:1px;'>" + saveSolar.substr(5, 2) + "." + saveSolar.substr(7, 2) + "</div>";
                
                // 확정된 일정표시
                for (let item of scheduleSet.keys()) {
                    if (item.split("-")[0] == dateParam) {
                        cell.innerHTML += "<div class='votedSchedule' style='font-size:13px'>" + item.split("-")[1] + "</div>";
                        //                        $('.votedSchedule').prevAll().css("background-color", "#ff8080");
                        console.log("#date" + parseInt(dateParam.substr(6)));
                        $("#date" + parseInt(dateParam.substr(6))).css("background-color", "#ff8080");
                    }
                }
                // 미확정된 일정표시
                for (let item of voteSet.keys()) {
                    if (item.split("-")[0] == dateParam) {
                        cell.innerHTML += "<div class='voteSchedule' style='font-size:13px'>" + item.split("-")[1] + "</div>";
                        console.log("#date" + parseInt(dateParam.substr(6)));
                        $("#date" + parseInt(dateParam.substr(6))).css("background-color", "#00ff80");
                    }
                }
                // 생일표시
                for (let item of birthSet.keys()) {
                    if (item.split("-")[0].substr(2, 4) == dateParam.substr(4)) {
                        cell.innerHTML += "<div class='memberBirthDay' style='font-size:13px'>" + item.split("-")[1] + "(" + item.split("-")[2] + ")님의 생일</div>";
                        console.log("#date" + parseInt(dateParam.substr(6)));
                        $("#date" + parseInt(dateParam.substr(6))).css("background-color", "#0080ff");
                    }
                }
                for (var markHoliday = 0; markHoliday < holiday.length; markHoliday++) {
                    var whatHoliday;
                    if (holiday[markHoliday].substr(0, 8) == parseInt(dateParam)) {
                        whatHoliday = holiday[markHoliday].substr(8, holiday[markHoliday].length - 8);
                        cell.innerHTML += "<div class='holi'>" + whatHoliday + "</div>";
                        $('.holi').css("color", "red");
                        $('.holi').prevAll().css("color", "red");
                    }
                }

                if (dateArr != null) {
                    for (var dateNum = 0; dateNum < dateArr.length; dateNum++) {
                        if (dateArr[dateNum] == dateParam) {
                            cell.innerHTML += "<div class='inputHoli'>" + titleArr[dateNum] + "</div>";
                            $('.inputHoli').css("color", "red");
                            $('.inputHoli').prevAll().css("color", "red");
                        }
                    }
                }
                cnt = cnt + 1;
                if (cnt % 7 == 0) {
                    // 1주일이 7일 이므로                 
                    row = calendar.insertRow(); // 줄 추가
                }
            }
        }
//  ================================ 달력 출력 끝! ================================




        // 일정들 전부 출력
        function getGroupInfo() {
            var para = document.location.href.split("?");
            //            console.log("para : " + para);
            //            console.log("para[1] : " + para[1]);
            para = para[1].split("=");
            //            console.log("para2 : " + para);
            //            
            //            para = par[1];
            //            console.log("click");
            $.ajax({
                type: 'post'
                , url: 'groupCal.do'
                , data: {
                    group: para[1]
                }
                , dataType: "json"
                , success: function (data) {
                    console.log(data);
                    //                    data[0].group_id;
                    //                    data[0].group_name;
                    //                    data[0].group_date;
                    //                    data[0].group_master;
                    //                    data[0].group_master_name;
                    //                    data[0].group_img;
                    //                    data[0].group_count;
                    //                    $("#icon").html = "<img src='" + data[0].group_img + "'>";
                    //                    document.getElementById("schedule").innerHTML += "<img src='" + data[0].group_img + "'>";
                    document.getElementById("icon").innerHTML = "<img src='" + data[0].group_img + "'>";
                    $("#title").html = "<span>" + data[0].group_name + "</span>";
                    document.getElementById("title").innerHTML += "<span>" + data[0].group_name + "</span>";
                    $("#master").html = "<span>" + data[0].group_master_name + "님</span>";
                    document.getElementById("master").innerHTML += "<span>" + data[0].group_master_name + "님</span>";
                    $("#otherMembers").html = "<span>외 " + data[0].group_count + "명</span>";
                    document.getElementById("otherMembers").innerHTML += "<span>외 " + data[0].group_count + "명</span>";
                    for (var arrCal = 1; arrCal < data.length; arrCal++) {
                        //                        console.log(data[arrCal].cal_num);
                        //                        console.log(data[arrCal].cal_date);
                        //                        console.log(data[arrCal].cal_group);
                        //                        console.log(data[arrCal].cal_memo);
                        //                        console.log(data[arrCal].cal_writer);
                        //                        console.log(data[arrCal].state_icon);
                        //                        console.log(data[arrCal].member_choice);
                        var str = "";
                        var str2 = data[arrCal].cal_date;
                        //                        str += "<div class='title'>";
                        //                        if(str2.indexOf(',') == -1)
                        //                        {
                        //                            str += "<div id='secheduleName'>단체 일정</div>";
                        //                        }
                        //                        else
                        //                        {
                        //                            str += "";
                        //                        }
                        str += '<div class="content">';
                        //                        str += '<div class="left">' + data[arrCal].state_icon + '</div>';
                        str += '<div class="left">';
                        if (str2.indexOf(',') == -1) {
                            str += data[arrCal].cal_date;
                            console.log("확정 : " + data[arrCal].cal_memo);
                            scheduleSet.add(data[arrCal].cal_date + "-" + data[arrCal].cal_memo);
                        }
                        else {
                            var res = str2.split(",");
                            str += res[0] + "~" + res[res.length - 1];
                            console.log("투표 : " + data[arrCal].cal_memo);
                            for (var i = 0; i < res.length; i++) {
                                voteSet.add(res[i] + "-" + data[arrCal].cal_memo);
                            }
                        }
                        str += '</div>';
                        str += '<div class="middle"><div class="up">' + data[arrCal].cal_memo + '</div><div class="down"><span>' + data[arrCal].cal_writer + '님</span><span>외 ' + data[0].group_count + '명 참여</span>';
                        str += '</div></div><div class="right"><div class="up"></div><div class="down"></div></div></div>';
                        if (str2.indexOf(',') == -1) {
                            $(".schedule").append(str);
                            //                            document.getElementById("schedule").innerHTML += str;
                        }
                        else {
                            $(".vote").append(str);
                            //                            document.getElementById("vote").innerHTML += str;
                        }
                        //                        console.log(str);
                    }
                    //                    console.log(data[1].member_choice);
                    var mem_choice = data[1].member_choice.split(",");
                    //                    var choiceInfo = new Array();
                    //                    for(var chNum = 0; chNum < mem_choice.length; chNum++){
                    //                       
                    //                        
                    ////                        console.log(mem_choice[chNum]);
                    ////                        console.log(mem_choice[chNum].substr(0 , mem_choice[chNum].length - 8)); //아이디
                    ////                        console.log(mem_choice[chNum].substr(mem_choice[chNum].length - 8 , 4)); //년
                    ////                        console.log(mem_choice[chNum].substr(mem_choice[chNum].length - 4 , 2)); //월
                    ////                        console.log(mem_choice[chNum].substr(mem_choice[chNum].length - 2 , 2)); //일
                    //                        
                    //                        
                    //                        
                    //                        
                    //                        choiceInfo.push(mem_choice[chNum].substr(0 , mem_choice[chNum].length - 8));
                    //                        choiceInfo.push(mem_choice[chNum].substr(mem_choice[chNum].length - 8 , 4));
                    //                        choiceInfo.push(mem_choice[chNum].substr(mem_choice[chNum].length - 4 , 2));
                    //                        choiceInfo.push(mem_choice[chNum].substr(mem_choice[chNum].length - 2 , 2));
                    //                        
                    //                    }
                    //                    for(var viewNum = 0; viewNum < choiceInfo.length; viewNum++){
                    //                        console.log(choiceInfo[viewNum]);
                    //                        
                    //                    }
                    //                    console.log(data[1].cal_date.split(","));
                    console.log(mem_choice);
                    buildCalendar();
                    //                    voteMeeting(data[1].cal_date.split(","));//투표중인 날짜표시
                    cal_date = data[1].cal_date.split(",");
                    voteMeeting(cal_date);
                }
                , error: function (req) {
                    //                    alert('통신실패');
                    alert("상태 : " + req.status + ", " + req.responseText + ", error : " + req.error);
                }
            })
        }
        // 모달 작업 부분
        function getGroupBrith() {
            var para = document.location.href.split("?");
            //            console.log("para : " + para);
            //            console.log("para[1] : " + para[1]);
            para = para[1].split("=");
            //            console.log("para2 : " + para);
            //            
            //            para = par[1];
            //            console.log("click");
            $.ajax({
                type: 'post'
                , url: 'groupBirth.do'
                , data: {
                    group: para[1]
                }
                , dataType: "json"
                , success: function (data) {
                    console.log("groupBirth");
                    console.log(data);
                    //                    console.log("이런");
                    //                    $(data).find(Object).each(function()
                    //                    {
                    //                        console.log($(this).find('memberNa    me').text());
                    //                    });
                    for (var i = 0; i < data.length; i++) {
                        //                        console.log(data[i].memberBirth + "-" + data[i].memberName + "-" + data[i].memberId);
                        var memBirth = data[i].memberBirth + "-" + data[i].memberName + "-" + data[i].memberId;
                        birthSet.add(memBirth);
                        //                        console.log(memBirth);
                        //                        console.log(birthSet[i]);
                    }
                    console.log("birthSet");
                    for (let item of birthSet.keys()) {
                        console.log(item.split("-"));
                        console.log("split : " + item.split("-")[0].substr(2, 4));
                    }
                }
                , error: function (req) {
                    //                    alert('통신실패');
                    alert("상태 : " + req.status + ", " + req.responseText + ", error : " + req.error);
                }
            })
        }
        // 확정된 일정 정보 출력
        function getScheduleInfo() {}
        // 미확정된 일정 정보 출력
        function getVoteInfo() {}

        function voteMeeting(cal_date) {
            //            console.log(cal_date);
            //            console.log($("#yearSelect option:selected").val() + $("#monthSelect option:selected").val());
            //            console.log(scheSet);
            for (var vote = 0; vote < cal_date.length; vote++) {
                $("#date" + parseInt(cal_date[vote].substr(6, 2))).children().last().html();
                
                if (cal_date[vote].substr(0, 4) == $("#yearSelect option:selected").val() && parseInt(cal_date[vote].substr(4, 2)) == parseInt($("#monthSelect option:selected").val())) {
                    $("#date" + parseInt(cal_date[vote].substr(6, 2))).css("backgroundColor", "red");
                    $("#date" + parseInt(cal_date[vote].substr(6, 2))).children().last().append("<div>투표중..</div>");
                    
                }
                //                for (let item of scheSet.keys()){             
                //                    if(item = cal_date[vote]){
                //                        if($('#yearSelect option:selected').val() == cal_date[vote].substr(0,4) && $('#monthSelect option:selected').val() == parseInt(cal_date[vote].substr(4,2))){
                //                        
                //                        $('#date' + parseInt(cal_date[vote].substr(6,2))).css("backgroundColor","green");
                //                        
                //
                //                        }
                //                        
                //                    }
                //
                //                    
                //
                //                } 
            }
        }
        
        function selectYes(){
//            console.log("!");
//            console.log(scheSet.toString());
            
            var groupId = location.href.split("=")[1];
            console.log(groupId);
            var calDate = "";
            var groupId = location.href.split("=")[1];
            console.log(groupId);
            
            for (let item of scheSet.keys())
                calDate += item + ",";
            
            var calTitle = $("#calTitle").val();
            console.log("");
            
             $.ajax({
                type: 'post'
                , url: 'ScheduleAdd.do'
                , data: {                    
                    title : calTitle,
                    calDate : calDate,
                    groupId : groupId
                    
                }
                , dataType: "json"
                , success: function (data) {
                    
                    console.log(data);
                }
                , error: function (req) {
                    
                    alert("상태 : " + req.status + ", " + req.responseText + ", error : " + req.error);
                }
            });
//                        
            location.reload();
            
        }
        
        function selectNo(){
            $("#modal").css("display","none");
            console.log("!!!!");
            $("#calTitle").val("");
            
        }
        
        function clickAddCal(){
            $("#selectAnswer").html("'" + $("#calTitle").val() + "'" + "의 제목으로 일정을 추가하시겠습니까?");
            var heights = $('#contentsHeight').css('height');
            if($("#calTitle").val() != ""){
                $('.modal').animate({
                    
                    height : heights ,

                }, 300);
            }
        }