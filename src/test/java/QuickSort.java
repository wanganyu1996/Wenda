import java.util.Random;

/**
 * Created by wanganyu on 2017/12/01.
 */
public class QuickSort {
   public void swap(int []A,int a,int b){
       int temp=A[a];
       A[a]=A[b];
       A[b]=temp;
   }
   public  int [] quickSort(int []A,int n){
       quick(A,0,n-1);
       return A;
   }
   private void quick(int A[],int start,int end){
       if(start>end)
           return;
       int mid=partition(A,start,end);
       quick(A,start,mid-1);
       quick(A,mid+1,end);
   }
    public int partition(int []A,int start,int end){
      /**随机交换**/
        Random rad=new Random();
        int n=end-start+1;
        int r=start+rad.nextInt(n);
        swap(A,end,r);
        //end
        int j=start;
        for(int i=start;i<end;i++){
            if(A[i]<A[end]){
                swap(A,i,j++);
            }
        }
        swap(A,end,j);
      return j;
   }
   public void party(int i){
       System.out.println(i);
   }
    public static void main(String[] args){
       QuickSort q=new QuickSort();
        int A[]={12,432,25,2,243,4,5,22,113};
       A=q.quickSort(A,A.length);
      for(int i=0;i<A.length;i++){
          System.out.print(A[i]+" ");
      }

    }
}
