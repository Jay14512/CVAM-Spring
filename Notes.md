## Short Version of ternary operator (? :)
private Sort buildDynamicSort(boolean ascending){
Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
return Sort.by(direction, "dateTime").and(Sort.by(direction, "appointmentId"));

## Long Version (if/else)
private Sort buildDynamicSort(boolean ascending) {
// 1. Declare variables to hold our sort direction and the final Sort object
Sort.Direction direction;
Sort finalSortStructure;

    // 2. Determine the direction explicitly using a standard if/else block
    if (ascending == true) {
        direction = Sort.Direction.ASC;
    } else {
        direction = Sort.Direction.DESC;
    }

    // 3. Create the primary sorting rule (Sort by the "dateTime" column first)
    Sort primarySort = Sort.by(direction, "dateTime");

    // 4. Create the secondary tie-breaker rule (Sort by the "appointmentId" column)
    Sort secondarySort = Sort.by(direction, "appointmentId");

    // 5. Combine them together so the database applies the tie-breaker second
    finalSortStructure = primarySort.and(secondarySort);

    // 6. Return the combined sorting object back to the repository
    return finalSortStructure;
}
