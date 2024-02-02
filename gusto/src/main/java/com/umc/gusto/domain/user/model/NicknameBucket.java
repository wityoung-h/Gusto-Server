package com.umc.gusto.domain.user.model;

public final class NicknameBucket {
    public static final String[] bucketA = {
            "귀여운", "용맹한", "용감한", "엘레강스한", "웃기는",
            "즐거운", "청량한", "고지식한", "산뜻한", "똑똑한",
            "명랑한", "상쾌한", "깨끗한", "노래하는", "소심한",
            "의심하는", "어지러운", "혼란스러운", "빛나는", "천진난만한"
    };
    public static final String[] bucketB = {
            "파스타", "뇨끼", "비빔밥", "곰탕", "감자탕",
            "수플레", "짬뽕", "탕수육", "야채곱창", "피자",
            "샐러드", "족발", "바질페스토", "도토리묵", "전복죽",
            "계란말이", "닭볶음탕", "추어탕", "단팥빵", "치즈케이크",
            "에그타르트"
    };

    public static int indexA = 0;
    public static int indexB = 0;

    public static String[] getNicknames() {
        String[] nicknames = new String[] {bucketA[indexA], bucketB[indexB]};

        indexA = (indexA + 1) % bucketA.length; // bucketA는 index 1씩 이동
        indexB = (indexB + 1) % bucketB.length; // bucketB는 index 3씩 이동

        return nicknames;
    }
}
