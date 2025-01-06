import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
export interface Produit {
  id: number;
  libelle: string;
  prix: number;
  qteStock: number;
}
@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private apiUrl = 'http://localhost:8080/api/produits';

  constructor(private http: HttpClient) {}

  ajoutproduit(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
