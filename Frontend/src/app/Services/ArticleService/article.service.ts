import { Injectable, Inject } from '@angular/core';
import { Article } from '../Objects/article';
import { Observable, of } from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {DOCUMENT} from '@angular/common';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  public article: Article[];
  private document: any;
  private baseurl: string;
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, @Inject(DOCUMENT) d,   private myRoute: Router)
  {
    this.document = d;
    this.http = http;
    this.baseurl = baseUrl;
  }

  getAllArticles(): Observable <Object> { 
    return this.http.get(this.baseurl + 'getAllArticles', {headers: this.headers});
  }


  getArticlePage(pageNumber:number, itemsPerPage:number): Observable <Object> {
    const params = new HttpParams()
                  .set('pageNumber', pageNumber.toString())
                  .set("itemsPerPage", itemsPerPage.toString());
                  
    return this.http.get(this.baseurl + 'getArticlePage', {headers: this.headers, params:params});
  }

  
  getFeaturedArticles(articleCount: number): Observable <Object> {
    const params = new HttpParams().set('articleCount', articleCount.toString());
    return this.http.get(this.baseurl + 'getArticlePage', {headers: this.headers, params:params});
  }

  getArticle(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(this.baseurl + 'getArticle', {headers: this.headers, params: params});
  }

  addArticle(article: Article): void {
    this.http.post(this.baseurl + 'addArticle', article)
      .subscribe((res) => {
      console.log(res);
      });
  }

  updateArticle(article: Article) {
    return this.http.post(this.baseurl + 'updateArticle', article);
  }

  updateArticleViews(article: Article) {
    return this.http.post(this.baseurl + 'updateArticleViews', article);
  }

  updateArticleAuthor(article: Article) {
    return this.http.post(this.baseurl + 'updateArticleAuthor', article);
  }

  deleteArticle(article: Article): Observable<any>{
    return this.http.post(this.baseurl + 'deleteArticle', article);
  }
}