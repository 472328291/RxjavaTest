// IBookManager.aidl
package com.example.rxjavatest;
import com.example.rxjavatest.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
     void addBook(in Book book);
}
