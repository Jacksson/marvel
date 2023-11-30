import { Component, OnInit } from '@angular/core';
import {MarvelService} from "../../services/marvel.service";
import {CharacterAggregate} from "../../models/character.model";

@Component({
  selector: 'app-character-list',
  templateUrl: './character-list.component.html',
  styleUrls: ['./character-list.component.css']
})
export class CharacterListComponent implements OnInit {
  characters: any[] | undefined;
  selectedCharacter: CharacterAggregate | null = null;

  constructor(private characterService: MarvelService) { }

  ngOnInit(): void {
    this.loadCharacters();
  }

  loadCharacters(): void {
    this.characterService.getCharacters().subscribe(
      (data: any) => {
        this.characters = data;
      },
      (error: any) => {
        console.error('Error al cargar personajes', error);
      }
    );
  }

  showCharacterDetails(character: any): void {
    console.log('Detalles del personaje:', character);
    this.selectedCharacter = character;
    this.characterService.setSelectedCharacter(character);
  }

  closeCharacterDetails() {
    this.selectedCharacter = null;
  }

}
