# Parameterize Search Queries for Flexible Product Searches

## Overview
This PR implements parameterized search query support for the ChargePoint automation script, addressing issue #2. The tool is now flexible and reusable for different product searches without requiring code modifications.

## Changes Made

### 1. **Updated Main.java**
   - Modified to accept command-line arguments for dynamic search terms
   - Implements a queue-based processing system for batch searches
   - Added default fallback ("boat blue") when no search term is provided
   - Enhanced error handling with try-catch blocks and meaningful error messages
   - Improved logging for better debugging and monitoring

### 2. **New SearchConfiguration.java**
   - Configuration management class with three-tier priority system:
     1. Command-line arguments (highest priority)
     2. Configuration file (search.properties)
     3. Default handling (fallback)
   - Methods:
     - `loadSearchQueries(args)`: Main entry point for loading queries
     - `loadFromConfigFile()`: Loads from properties file
     - `saveQueriesToConfigFile()`: Saves queries to properties file
   - Full error handling and user feedback

### 3. **New search.properties**
   - Configuration file for batch search scenarios
   - Format: `search.queries=query1,query2,query3`
   - Pre-configured with default "boat blue" example
   - Includes usage examples in comments

## Usage Examples

### Command-line Usage
```bash
# Single search
java -cp "target/classes:target/dependency/*" Main "wireless headphones"

# Multiple searches (batch processing)
java -cp "target/classes:target/dependency/*" Main "boat blue" "bluetooth speakers" "phone"
```

### Configuration File Usage
Edit `search.properties`:
```properties
search.queries=boat blue,wireless headphones,bluetooth speakers,phone
```

Then run:
```bash
java -cp "target/classes:target/dependency/*" Main
```

### Default Behavior
```bash
# Uses default "boat blue" if no args or config provided
java -cp "target/classes:target/dependency/*" Main
```

## Acceptance Criteria - All Met ✅
- [x] CLI supports passing search terms as arguments
- [x] Configuration file support for defining multiple search queries
- [x] Default search term if none is provided
- [x] Proper error handling for invalid or empty search terms

## Benefits
- ✨ **Flexibility**: Search for any product without code modifications
- 📦 **Batch Processing**: Process multiple searches in one execution
- 🔄 **Reusability**: Integrate with automation frameworks easily
- 🛡️ **Robustness**: Enhanced error handling and validation
- 📝 **Configuration**: Easy setup via properties file or CLI args

## Testing
The implementation:
- Handles empty/null arguments gracefully
- Validates search terms before processing
- Skips invalid entries with warnings
- Provides clear console output for debugging
- Maintains backward compatibility with existing code

## Related Issue
Closes #2
