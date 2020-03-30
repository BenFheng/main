package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_FILTER;

import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Arrays;

import seedu.expensela.logic.commands.FilterCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_CATEGORY);

        if (!(argMultimap.getValue(PREFIX_CATEGORY).isPresent() || argMultimap.getValue(PREFIX_DATE).isPresent())
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER, FilterCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
                // set filter
                String cat = argMultimap.getValue(PREFIX_CATEGORY).get().trim();
                // sends the next word after "category" to see if it matches any transaction categories
                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)));
            }

            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
                // sends the next word after "date" to see if it matches any transaction dates
                String date = argMultimap.getValue(PREFIX_DATE).get().trim();
                return new FilterCommand(new DateEqualsKeywordPredicate(Arrays.asList(date)));
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

}
