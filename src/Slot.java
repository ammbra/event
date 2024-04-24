//TODO have slots for event sessions with a timeframe
//TODO a timeframe has a start and end time
//TODO a session can be a conference, a keynote, a workshop
//TODO a conference has a title, a duration and a speaker
//TODO a keynote has a title, a theme, a duration and a speaker
//TODO a workshop has a title, steps to execute, a duration and a speaker
record Slot<Session, Timeframe>(Session session, Timeframe timeframe) {}
