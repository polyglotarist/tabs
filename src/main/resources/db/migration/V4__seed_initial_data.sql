INSERT INTO artist (name, bio) VALUES
  ('Neil Young', 'Canadian singer-songwriter known for Harvest and Heart of Gold.'),
  ('Simon & Garfunkel', 'American folk-rock duo known for The Sound of Silence.'),
  ('Fleetwood Mac', 'British-American rock band known for Rumours.');

INSERT INTO song (artist_id, title, original_key, capo, tempo, difficulty, body) VALUES
  (
    (SELECT id FROM artist WHERE name = 'Neil Young'),
    'Heart of Gold', 'G', 2, 120, 'Beginner',
    '[G]I want to live, I want to [Em7]give
[G]I''ve been a [Em7]miner for a [G]heart of gold
[D]It''s these expressions I never [C]give
[G]That keep me [Em7]searching for a [G]heart of gold'
  ),
  (
    (SELECT id FROM artist WHERE name = 'Simon & Garfunkel'),
    'The Sound of Silence', 'Am', 0, 105, 'Intermediate',
    '[Am]Hello darkness, my old [G]friend
[Am]I''ve come to talk with [G]you again
[F]Because a vision softly [C]creeping
[F]Left its seeds while I was [C]sleeping'
  ),
  (
    (SELECT id FROM artist WHERE name = 'Fleetwood Mac'),
    'Landslide', 'C', 3, 85, 'Intermediate',
    '[C]I took my love, I took it [G]down
[Am]I climbed a mountain and I [F]turned around
[C]And I saw my reflection in the [G]snow-covered hills
[F]Till the landslide [C]brought me [G]down'
  );