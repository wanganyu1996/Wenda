/**
 * Created by wanganyu on 2017/12/02.
 */
public class MergeSort {

    public static void sort(int A[], int left, int right) {
        if (left >= right)
            return;
        int middle = (left + right) / 2;
        sort(A, left, middle);
        sort(A, middle + 1, right);
        mergeSort(A, left, middle, right);
    }

    public static void mergeSort(int A[], int left, int middle, int right) {
       int l=left,r=middle+1;
       int index=left;
       int temp[]=new int[A.length];
       while(left<=middle&&r<=right){
           if(A[left]<=A[r]){
              temp[index++]=A[left++];
           }else{
               temp[index++]=A[r++];
           }
       }
       while(left<=middle){
           temp[index++]=A[left++];
       }
       while(r<=right){
           temp[index++]=A[r++];
       }

       while(l<=right){
           A[l]=temp[l];
           l++;
       }
    }


    public static void main(String[] args) {
      int a[]={12,4,2342,24,42,2,46,74,64,7,544,6,645,466,466,46};
        sort(a,0,a.length-1);
        for(int i=0;i<a.length;i++){
            System.out.printf(a[i]+" ");
        }
    }
}
