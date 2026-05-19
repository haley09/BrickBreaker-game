# TODO - Brick Breaker

## Gameplay
- Add levels with different brick layouts.
- Increase ball speed as the score grows.
- Add power-ups such as wider paddle, extra life, or multi-ball.
- Add a pause state instead of using Escape to exit immediately.

## UI
- Add instructions to the menu screen.
- Add a high score display.
- Improve game-over and win screens with buttons instead of only keyboard restart.

## Code
- Move magic layout values into named constants.
- Make `MapGenerator` fields private and expose only needed methods.
- Add a small game state enum for menu, playing, paused, won, and game over.

## Testing
- Test paddle collision at the left and right edges.
- Test restart after win and after losing all lives.
- Test running from a clean compile with no `.class` files.

