package com.example.bank.service;

import com.example.bank.dto.RoleDto;
import com.example.bank.dto.UserDto;
import com.example.bank.mapper.UserMapper;
import com.example.bank.repo.UserRepo;
import com.example.bank.util.error.NotFoundException;
import com.example.bank.util.error.NotSaveException;
import com.example.bank.util.error.NullRequiredFieldException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author Filippova_Viktoria
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepo repo;
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Override
    public Optional<UserDto> update(@NonNull UserDto userDto) {
        log.info("Start update user: {}", userDto);
        return isBlank(userDto)
                .map(e -> {
                    isExists(e.getId());
                    return save(e);
                });
    }

    @Override
    public Optional<Set<UserDto>> getUsers() {
        log.info("Start found all users");
        Set<UserDto> dtoSet = mapper.entitySetToDtoSet(repo.findAll());
        if (dtoSet.isEmpty()) {
            log.error("Objects do not exists!");
            return Optional.empty();
        } else {
            log.info("Was found {} users", dtoSet.size());
            return Optional.of(dtoSet);
        }
    }

    @Override
    public Optional<UserDto> addUserBase(@NonNull UserDto userDto) {
        log.info("Start add user: {}", userDto);
        return isBlank(setDefaultRole(userDto))
                .map(this::save);
    }

    private UserDto save(@NonNull UserDto userDto) {
        log.info("Start save user: {}", userDto);
        return Optional.ofNullable(mapper.entityToDto(repo.save(mapper.dtoToEntity(userDto))))
                .map(e -> {
                    log.info("Entity was saved successful: {}", e);
                    return e;
                })
                .orElseThrow(() -> new NotSaveException(userDto, userDto.getId()));
    }

    private Optional<UserDto> getUserById(@NonNull Long id) {
        log.info("Start found user by id: {}", id);
        return Optional.ofNullable(repo.findById(id)
                .map(userEntity -> {
                    UserDto userDto = mapper.entityToDto(userEntity);
                    log.info("Object was found successful: {}", userDto);
                    return userDto;
                })
                .orElseThrow(() -> new NotFoundException(id)));
    }

    private static UserDto setDefaultRole(UserDto userDto) {
        return userDto.toBuilder()
                .role(RoleDto.builder().roleId(1L).build())
                .build();
    }

    private static Optional<UserDto> isBlank(UserDto userDto) {
        boolean check = userDto.getUsername() == null
                || userDto.getPassword() == null
                || userDto.getClient() == null
                || userDto.getClient().getFirstname() == null
                || userDto.getClient().getSurname() == null
                || userDto.getRole().getRoleId() == null;

        if (check) {
            throw new NullRequiredFieldException("User");
        }
        return Optional.of(userDto);
    }

    private void isExists(@NonNull Long id) {
        getUserById(id);
    }

    @Override
    public Optional<UserDto> getUser(@NonNull String username) {
        log.info("Start found user with username: {}", username);
        return repo.findByUsername(username)
                .map(userEntity -> {
                    UserDto userDto = mapper.entityToDto(userEntity);
                    log.info("Object was found successful: {}", userDto);
                    return Optional.of(userDto);
                })
                .orElseThrow(() -> new NotFoundException(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getUser(username).get();
    }
}
