package sql;

public class Sqlstatement {
    private String selectString;
    private String fromString;
    private String whereString;
    private String GroupByString;
    private String OrderbyString;
    private String havingString;
    private String updateString;
    private String deleteString;
    private String insertSrting;
    private String valuesString;

    private String user;
    private String passWord;
    private String role;
    




    private static String[] selects = {"select","from","where","group by","having","order by"};

    public Sqlstatement() {
    }

    public static String select(String selectString,String fromString,String whereString,String groupByString,String havingString,String orderbyString)
    {
        String temp = "";
        temp+=selects[0]+" "+selectString+" ";
        temp+=selects[1]+" "+fromString+" ";
        temp+=selects[2]+" "+whereString+" ";
        temp+=selects[3]+" "+groupByString+" ";
        temp+=selects[4]+" "+havingString+" ";
        temp+=selects[5]+" "+orderbyString+" ";
        return temp;
    }
    public static String select(String[] selectInfo)
    {
        String sql="";
        for(int i=0;i<selectInfo.length;i++)
        {
            if (i<2)
            {
                sql+=selects[i]+" "+selectInfo[i]+" ";
                continue;
            }
            else {
                if (selectInfo[i].isEmpty())
                {
                    continue;
                }
                else {
                    sql+=selects[i]+" "+selectInfo[i]+" ";
                }
            }
        }
        return sql;
    }

    public static String select(String selectString,String fromString,String whereString,String groupByString,String havingString)
    {
        String temp = "";
        temp+=selects[0]+" "+selectString+" ";
        temp+=selects[1]+" "+fromString+" ";
        temp+=selects[2]+" "+whereString+" ";
        temp+=selects[3]+" "+groupByString+" ";
        temp+=selects[4]+" "+havingString+" ";
        return temp;
    }
    public static String select(String selectString,String fromString,String whereString,String groupByString)
    {
        String temp = "";
        temp+=selects[0]+" "+selectString+" ";
        temp+=selects[1]+" "+fromString+" ";
        temp+=selects[2]+" "+whereString+" ";
        temp+=selects[3]+" "+groupByString+" ";
        return temp;
    }
    public static String select(String selectString,String fromString,String whereString)
    {
        String temp = "";
        temp+=selects[0]+" "+selectString+" ";
        temp+=selects[1]+" "+fromString+" ";
        temp+=selects[2]+" "+whereString+" ";
        return temp;
    }
    public static String select(String selectString,String fromString)
    {
        String temp = "";
        temp+=selects[0]+" "+selectString+" ";
        temp+=selects[1]+" "+fromString+" ";
        return temp;
    }
}
