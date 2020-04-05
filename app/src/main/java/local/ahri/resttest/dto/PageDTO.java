package local.ahri.resttest.dto;

import java.io.Serializable;
import java.util.List;

public class PageDTO<T> implements Serializable {
    private List<T> content;
    private boolean last;  //마지막 페이지 여부
    private int totalPages;  //전체 페이지 개수
    private int totalElements;  //전체 데이터 개수
    private int size;  //한 페이지에 몇개
    private int number;  //페이지 넘버
    private int numberOfElements;  //지금 페이지에 있는 데이터 개수

    public PageDTO(){    }

    public PageDTO(List<T> content, boolean last, int totalPages, int totalElements, int size, int number, int numberOfElements){
        this.content = content;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.number = number;
        this.numberOfElements = numberOfElements;
    }

    public List<T> getContent(){ return  content;}
    public void setContent(List<T> content) { this.content = content; }

    public boolean getLast() { return last; }
    public void setLast(boolean last) { this.last = last; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getTotalElements() { return totalElements; }
    public void setTotalElements(int totalElements) { this.totalElements = totalElements; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getNumberOfElements() { return numberOfElements; }
    public void setNumberOfElements(int numberOfElements) { this.numberOfElements = numberOfElements; }
}
