package com.mycompany.ltstriptermsmaven;

import java.util.Comparator;

public class LTSTripTermsRow implements Comparator<LTSTripTermsRow> {
    String tripStatusCode,tripId,detailId,customerUploadName,application,f_Column,
            g_Column,h_Column,i_Column,j_Column,k_Column,l_Column,
            m_Column,n_Column,o_Column,p_Column,q_Column,r_Column,s_Column,t_Column;
    
    LTSTripTermsRow(){}
    
    LTSTripTermsRow(String a,String b,String c,String d,String e,String f,
            String g,String h,String i,String j,String k,String l,
            String m,String n,String o,String p,String q,String r, String s, String t){
        
        this.tripStatusCode = a;
        this.tripId = b;
        this.detailId = c;
        this.customerUploadName = d;
        this.application = e;
        this.f_Column = f;
        this.g_Column = g;
        this.h_Column = h;
        this.i_Column = i;
        this.j_Column = j;
        this.k_Column = k;
        this.l_Column = l;
        this.m_Column = m;
        this.n_Column = n;
        this.o_Column = o;
        this.p_Column = p;
        this.q_Column = q;
        this.r_Column = r;
        this.s_Column = s;
        this.t_Column = t;
        
    }
    
    LTSTripTermsRow(String[] inputArray){
        this.tripStatusCode = inputArray[0];
        this.tripId = inputArray[1];
        this.detailId = inputArray[2];
        this.customerUploadName = inputArray[3];
        this.application = inputArray[4];
        this.f_Column = inputArray[5];
        this.g_Column = inputArray[6];
        this.h_Column = inputArray[7];
        this.i_Column = inputArray[8];
        this.j_Column = inputArray[9];
        this.k_Column = inputArray[10];
        this.l_Column = inputArray[11];
        this.m_Column = inputArray[12];
        this.n_Column = inputArray[13];
        this.o_Column = inputArray[14];
        this.p_Column = inputArray[15];
        this.q_Column = inputArray[16];
        this.r_Column = inputArray[17];
        this.s_Column = inputArray[18];
        this.t_Column = inputArray[19];
        
        
    }

    public String getTripStatusCode() {
        return tripStatusCode;
    }

    public String getTripId() {
        return tripId;
    }

    public String getDetailId() {
        return detailId;
    }

    public String getCustomerUploadName() {
        return customerUploadName;
    }

    public String getApplication() {
        return application;
    }

    public String getF_Column() {
        return f_Column;
    }

    public String getG_Column() {
        return g_Column;
    }

    public String getH_Column() {
        return h_Column;
    }

    public String getI_Column() {
        return i_Column;
    }

    public String getJ_Column() {
        return j_Column;
    }

    public String getK_Column() {
        return k_Column;
    }

    public String getL_Column() {
        return l_Column;
    }

    public String getM_Column() {
        return m_Column;
    }

    public String getN_Column() {
        return n_Column;
    }

    public String getO_Column() {
        return o_Column;
    }

    public String getP_Column() {
        return p_Column;
    }

    public String getQ_Column() {
        return q_Column;
    }

    public String getR_Column() {
        return r_Column;
    }
    
    public String getS_Column() {
        return s_Column;
    }
    
    public String getT_Column() {
        return t_Column;
    }

    

    public void setTripStatusCode(String tripStatusCode) {
        this.tripStatusCode = tripStatusCode;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public void setCustomerUploadName(String customerUploadName) {
        this.customerUploadName = customerUploadName;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setF_Column(String f_Column) {
        this.f_Column = f_Column;
    }

    public void setG_Column(String g_Column) {
        this.g_Column = g_Column;
    }

    public void setH_Column(String h_Column) {
        this.h_Column = h_Column;
    }

    public void setI_Column(String i_Column) {
        this.i_Column = i_Column;
    }

    public void setJ_Column(String j_Column) {
        this.j_Column = j_Column;
    }

    public void setK_Column(String k_Column) {
        this.k_Column = k_Column;
    }

    public void setL_Column(String l_Column) {
        this.l_Column = l_Column;
    }

    public void setM_Column(String m_Column) {
        this.m_Column = m_Column;
    }

    public void setN_Column(String n_Column) {
        this.n_Column = n_Column;
    }

    public void setO_Column(String o_Column) {
        this.o_Column = o_Column;
    }

    public void setP_Column(String p_Column) {
        this.p_Column = p_Column;
    }

    public void setQ_Column(String q_Column) {
        this.q_Column = q_Column;
    }

    public void setR_Column(String r_Column) {
        this.r_Column = r_Column;
    }
    
    public void setS_Column(String s_Column) {
        this.s_Column = s_Column;
    }
     
    public void setT_Column(String t_Column) {
        this.t_Column = t_Column;
    }
    
    
    

    public String[] gvpReturnArrayContent(String timeToUseInColumnB,String statusToPrintInDColumn){
        String[] tokensOfColumnH = h_Column.split(",");
        String cityFromColumnH = tokensOfColumnH[0];
        String StateFromColumnH = tokensOfColumnH[1];
        
        String[] arrayOfContent;
        
        
        switch (timeToUseInColumnB) {
            case "l_Column":
                arrayOfContent = new String[] {f_Column, l_Column,i_Column, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, t_Column,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
            case "k_Column":
                arrayOfContent = new String[] {f_Column, k_Column,i_Column, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, t_Column,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
            case "m_Column":
                arrayOfContent = new String[] {f_Column, m_Column,i_Column, statusToPrintInDColumn,
                    cityFromColumnH, StateFromColumnH, t_Column,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,"CLM",null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,tripId} ;
                return arrayOfContent;
        }
        
        return arrayOfContent = new String[] {"Problem in gvpReturn",null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
        
    }
    
    public String[] csvReturnArrayContent(){
        String[] arrayOfContent = new String[] {tripStatusCode,tripId,detailId,
            customerUploadName,application,f_Column,g_Column,h_Column,i_Column,j_Column,
            k_Column,l_Column,m_Column,n_Column,o_Column,p_Column,q_Column,
            r_Column,s_Column,t_Column,} ;
        
        return arrayOfContent;
        
    }
    
    public String[] mmtsReturnArrayContent(String timeToUseInColumnC,String statusToPrintInCodeColumn){
        
        String[] tokensOfColumnH = h_Column.split(",");
        String cityFromColumnH = tokensOfColumnH[0];
        String StateFromColumnH = tokensOfColumnH[1];
        String[] arrayOfContent;
        
        switch (timeToUseInColumnC) {
            case "l_Column":
            {
                    arrayOfContent = new String[]{f_Column, null,l_Column,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,i_Column,
                    null,t_Column,null,null,null,null,null,null,null,null,"H"};
                return arrayOfContent;
            }
            case "k_Column":
            {
                    arrayOfContent = new String[]{f_Column, null,k_Column,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,i_Column,
                    null,t_Column,null,null,null,null,null,null,null,null,"H"};
                return arrayOfContent;
            }
            case "m_Column":
            {
                    arrayOfContent = new String[]{f_Column, null,m_Column,
                    statusToPrintInCodeColumn, cityFromColumnH,StateFromColumnH,null,i_Column,
                    null,t_Column,null,null,null,null,null,null,null,null,"H"};
            return arrayOfContent;
            }
        }
        
         
        return arrayOfContent = new String[] {"Problem in mmtsReturn",null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
        
    }

    @Override
    public int compare(LTSTripTermsRow o1, LTSTripTermsRow o2) {
        
        String stringA = o1.getF_Column();
        String stringB = o2.getF_Column();
   
        return stringA.compareTo(stringB);
    }
}