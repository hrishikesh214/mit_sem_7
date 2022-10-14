
import itertools
from pprint import pprint


s1 = 'send'
s2 = 'more'
s3 = 'money'


chars = list(set(s1+s2+s3))
chars.sort()
chars


def bruteforce():
    digits = range(10)
    for perm in itertools.permutations(digits, len(chars)):
      # Crypt arithmetic uses any unique combination of chars and digits
        sol = dict(zip(chars, perm))
        if sol['s'] == 0 or sol['m'] == 0:
            continue
        send = 1000 * sol['s'] + 100 * sol['e'] + 10 * sol['n'] + sol['d']
        more = 1000 * sol['m'] + 100 * sol['o'] + 10 * sol['r'] + sol['e']
        money = 10000 * sol['m'] + 1000 * sol['o'] + \
            100 * sol['n'] + 10 * sol['e'] + sol['y']
        if send + more == money:
            print("bruteforce: SEND + MORE = MONEY")
            return send, more, money


print(bruteforce())


def addLiterals(a1, a2):
    l = len(a1)
    result = [None] * l
    carry = 0
    for i in reversed(range(l)):
        if a1[i] is None or a2[i] is None:
            carry = 0
            continue
        result[i] = a1[i] + a2[i] + carry
        if result[i] >= 10:
            result[i] -= 10
            carry = 1
        else:
            carry = 0
    if a1[0] is None or a2[0] is None:
        return [None] + result
    return [carry] + result


def replace(string, mapping):
    return [mapping.get(string[i], None) for i in range(len(string))]


def matches(result1, result2):
    for i, v1 in enumerate(result1):
        v2 = result2[i]
        if v2 != v1 and v2 is not None and v1 is not None and \
                not (i+1 < len(result2) and result2[i+1] is None and v1 == v2 + 1):
            return False
    return True


def isValid(mapping):

    # Verify that the mapping does not start with 0.
    if mapping.get(s3[0], None) == 0:
        return False

    # Check that the mapping is arithmetically correct.
    summ = addLiterals(replace(s1, mapping), replace(s2, mapping))
    return matches(replace(s3, mapping), summ)


def valueCount(mapping, c):
    m = dict(mapping)
    count = 0
    for i in possibleValues(mapping):
        m[c] = i
        if isValid(m):
            count += 1
    return count


def possibleValues(mapping):
    vals = [mapping[key] for key in mapping]
    for i in range(10):
        if i not in vals:
            yield i


def mostRestrainedVariable(mapping):
    min_count = 10000
    vals = [mapping[key] for key in mapping]
    result = None
    for c in chars:
        if c not in mapping:
            count = valueCount(mapping, c)
            if count < min_count:
                min_count = count
                result = c
    return result


def leastConstrainedOrdering(mapping, c):

    def howgood(i):
        m = dict(mapping)
        m[c] = i
        return valueCount(m, mostRestrainedVariable(m))

    ordering = list(possibleValues(mapping))
    ordering.sort(key=howgood)
    return reversed(ordering)


def solveSendMoreMoney(mapping):
    if not isValid(mapping):
        return False

    mapping = dict(mapping)
    if len(mapping) == len(chars):
        print('\n\nwith backtracking: ')
        pprint(mapping)
        return
    c = mostRestrainedVariable(mapping)
    for i in leastConstrainedOrdering(mapping, c):
        mapping[c] = i
        solveSendMoreMoney(mapping)


solveSendMoreMoney({})
