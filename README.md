# Sed's java implementation

A non-production project to implement Linux's [SED](https://www.gnu.org/software/sed/manual/sed.html) in Java. 
It supports following options of SED command

- Basic regex substitution using `s///g`
- Support for `-n`
  - `-n '2,4p'` - Prints the specified line number 
  - `-n /pattern/p filename` - Prints the line which satisy the pattern
- `G` - Add double-spacing to a file
- `/^$/d` - Strip trailing blank lines from file
- `-i s/Search/ReplaceWith/g` - Edit in-place
