import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CharacterListComponent} from "./components/character-list/character-list.component";
import {CharacterDetailsComponent} from "./components/character-details/character-details.component";
import {QueryLogComponent} from "./components/query-log/query-log.component";

const routes: Routes = [
  { path: '', redirectTo: '/characters', pathMatch: 'full' },
  { path: 'characters', component: CharacterListComponent },
  { path: 'character/:id', component: CharacterDetailsComponent },
  { path: 'log', component: QueryLogComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
