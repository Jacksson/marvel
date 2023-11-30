export interface CharacterAggregate {
  id: number;
  name: string;
  description: string;
  thumbnail: Thumbnail;
  // Agrega más propiedades según las características de tu modelo

  comics: Comic[];
  series: Series[];
}

interface Thumbnail {
  path: string;
  extension: string;
}

interface Comic {
  id: number;
  title: string;
}

interface Series {
  id: number;
  title: string;
}
