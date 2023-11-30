import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {MarvelService} from "../../services/marvel.service";
import {CharacterAggregate} from "../../models/character.model";

@Component({
  selector: 'app-character-details',
  templateUrl: './character-details.component.html',
  styleUrls: ['./character-details.component.css']
})
export class CharacterDetailsComponent implements OnInit {
  @Input() selectedCharacter: CharacterAggregate | undefined;
  @Output() close = new EventEmitter<void>();
  character: CharacterAggregate | undefined;

  constructor(
    private route: ActivatedRoute,
    private characterService: MarvelService
  ) { }

  ngOnInit(): void {
    this.characterService.getSelectedCharacter().subscribe(character => {
      this.selectedCharacter = character;
    });
   // this.loadCharacter();
  }

  closeDetails() {
    this.close.emit();
  }

  private loadCharacter(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.characterService.getCharacterById(id)
      .subscribe((character: any) => this.character = character);
  }
}
