import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarvelService {
  private selectedCharacterSubject = new BehaviorSubject<any>(null);
  selectedCharacter$: Observable<any> = this.selectedCharacterSubject.asObservable();

  private apiUrl = '/api/characters';

  constructor(private http: HttpClient) { }

  getCharacters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getCharacterById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  /* Character State*/
  setSelectedCharacter(character: any): void {
    this.selectedCharacterSubject.next(character);
  }

  getSelectedCharacter(): Observable<any> {
    return this.selectedCharacterSubject.asObservable();
  }
}
