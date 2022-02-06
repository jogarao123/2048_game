package com.game;

import java.util.*;

public class Main {
    static int ROWS=4,COLS=4;
    static int a[][]=new int[4][4];
    static Random random=new Random();
    static  Scanner sc=new Scanner(System.in);

    static int getEmptyCount(){
        int cnt=0;
        int i,j;
        for(i=0;i<ROWS;i++)
            for (j=0;j<COLS;j++)
                if(a[i][j]==0)
                    cnt++;
        return  cnt;
    }
    static void show(){
        int i,j;
        for(i=0;i<ROWS;i++)
        {    for(j=0;j<COLS;j++)
                System.out.print(a[i][j]+"  ");
            System.out.println();
        }
    }
    static int getNewNumber(){
        return random.nextInt(2)==0?2:4;
    }

    static  void putNewNumber(){
        //finds number of empty cells in the 2D array
        //generated some random number from 1 to Number_of_emptyCells
        //assigns (2 or 4 randomly) to that empty Cell
        int empty=getEmptyCount();
        if(empty==0)return;
        int pos=1+random.nextInt(empty);
        for(int row=0;row<ROWS;row++)
            for(int col=0;col<COLS;col++)
                if(a[row][col]==0) {
                  pos--;
                  if(pos==0){
                      a[row][col]=getNewNumber();
                      return;
                  }
                }
    }
    static List<Integer>  mergeRow(List<Integer> list){
        //takes a list and return merged list
        //examples
        // [2,4,4,8] -> [2,8,8]
        //[2,4,8] -> [2,4,8]
        List<Integer> mergeList=new ArrayList<>();
        int i;
        boolean merged=false;
        for(i=0;i<list.size();i++){
            if(!merged){
                if(i+1<list.size() && list.get(i)==list.get(i+1)){
                    mergeList.add(list.get(i)*2);
                    merged=true;
                    i++;
                    continue;
                }
            }
            mergeList.add(list.get(i));
        }
        return  mergeList;
    }

    static  void moveLeft(){
        //for every row
        //make a list of numbers [n1,n2,n3,n4]
        // merge each list seperately and get new merged list
        // reassign the row to the main 2D array
        //if no change happened dont put new element
        boolean changed=false;
        for(int row=0;row<ROWS;row++) {
            List<Integer> list = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                if (a[row][col] > 0)
                    list.add(a[row][col]);
            }
            List<Integer> mergedRow = mergeRow(list);
            for (int col = 0; col < COLS; col++) {
                int prevValue=a[row][col];
                if (col < mergedRow.size())
                    a[row][col] = mergedRow.get(col);
                else
                    a[row][col] = 0;
                if(prevValue!=a[row][col])
                    changed=true;
            }
        }
        if(changed) putNewNumber();
    }
    static  void moveRight(){
        boolean changed=false;
        for(int row=0;row<ROWS;row++){
            List<Integer> list=new ArrayList<>();
            for(int col=COLS-1;col>=0;col--){
                if(a[row][col]>0)
                    list.add(a[row][col]);
            }
            List<Integer> mergedRow=mergeRow(list);

            for(int col=COLS-1,indx=0;col>=0;col--,indx++){
                int prevValue=a[row][col];

                if(indx<mergedRow.size())
                    a[row][col]=mergedRow.get(indx);
                else
                    a[row][col]=0;
                if(prevValue!=a[row][col])
                    changed=true;
            }
        }
        if(changed) putNewNumber();
    }
    static  void moveDown(){
        boolean changed=false;
        for(int col=0;col<COLS;col++){
            List<Integer> list=new ArrayList<>();
            for(int row=ROWS-1;row>=0;row--){
                if(a[row][col]>0){
                    list.add(a[row][col]);
                }
            }
            List<Integer> mergedRow=mergeRow(list);

            for(int row=ROWS-1,indx=0;row>=0;row--,indx++){
                int prevValue=a[row][col];

                if(indx<mergedRow.size())
                    a[row][col]=mergedRow.get(indx);
                else a[row][col]=0;
                if(prevValue!=a[row][col])
                    changed=true;
            }
        }
        if(changed) putNewNumber();
    }
    static void moveUp(){
        boolean changed=false;
        for(int col=0;col<COLS;col++){
            List<Integer>list =new ArrayList<>();
            for(int row=0;row<ROWS;row++){
                if(a[row][col]>0)
                    list.add(a[row][col]);
            }
            List<Integer> mergedRow=mergeRow(list);

            for(int row=0;row<ROWS;row++){
                int prevValue=a[row][col];

                if(row<mergedRow.size())
                    a[row][col]=mergedRow.get(row);
                else
                    a[row][col]=0;
                if(prevValue!=a[row][col])
                    changed=true;
            }
        }
        if(changed) putNewNumber();
    }

    static  boolean isGameOver(){
        if(getEmptyCount()<ROWS*COLS)
            return  false;
        for(int row=0;row<ROWS;row++)
            for(int col=0;col<COLS;col++){
                if(col+1<COLS){
                    if(a[row][col]==a[row][col+1])
                        return  false;
                }
                if(row+1<ROWS){
                    if(a[row][col]==a[row+1][col])
                        return  false;
                }
            }
        return  true;
    }

    static  int takeInput(){
        System.out.println("Enter 1.Left,2.Right,3.Up,4.Down:");
        int val=sc.nextInt();
        return  val;
    }

     static boolean isSuccess() {
        for(int row=0;row<ROWS;row++)
            for(int col=0;col<COLS;col++)
                if(a[row][col]==2048)return  true;
        return  false;
    }
    public static void main(String[] args) {

        putNewNumber();
        do{
            show();
            int option=takeInput();
            switch (option){
                case (1):moveLeft();
                break;
                case (2):moveRight();
                break;
                case (3):moveUp();
                break;
                case (4):moveDown();
                break;
                default:
                    System.out.println("---------------------------enter valid option -----------------------------------");
            }
            if(isSuccess()){
                System.out.println("Congratulations ! You made It");
                break;
            }
        }while(isGameOver()!=true);
    }


}
