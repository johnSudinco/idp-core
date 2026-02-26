CREATE SCHEMA IF NOT EXISTS audit;


ALTER SCHEMA audit OWNER TO postgres;

--
-- TOC entry 7 (class 2615 OID 16390)
-- Name: auth; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS auth;


ALTER SCHEMA auth OWNER TO postgres;

--
-- TOC entry 8 (class 2615 OID 16391)
-- Name: config; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS config;


ALTER SCHEMA config OWNER TO postgres;

--
-- TOC entry 9 (class 2615 OID 16392)
-- Name: tenant; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS tenant;


ALTER SCHEMA tenant OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 16393)
-- Name: audit_logs; Type: TABLE; Schema: audit; Owner: postgres
--

CREATE TABLE audit.audit_logs (
                                  id bigint NOT NULL,
                                  actor_user_id bigint,
                                  action character varying(255) NOT NULL,
                                  target_type character varying(255),
                                  target_id bigint,
                                  correlation_id character varying(255),
                                  ip_address character varying(255),
                                  user_agent character varying(255),
                                  metadata character varying(255),
                                  created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE audit.audit_logs OWNER TO postgres;

--
-- TOC entry 3698 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE audit_logs; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON TABLE audit.audit_logs IS 'Registro de auditoría general de acciones en el IdP';


--
-- TOC entry 3699 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.id IS 'Identificador único del log de auditoría';


--
-- TOC entry 3700 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.actor_user_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.actor_user_id IS 'Usuario que ejecutó la acción (null si sistema)';


--
-- TOC entry 3701 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.action; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.action IS 'Acción registrada (ej. LOGIN_SUCCESS, TOKEN_ISSUED)';


--
-- TOC entry 3702 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.target_type; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.target_type IS 'Tipo de recurso afectado (USER, CLIENT, TOKEN, etc.)';


--
-- TOC entry 3703 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.target_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.target_id IS 'Identificador del recurso afectado';


--
-- TOC entry 3704 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.correlation_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.correlation_id IS 'ID para correlación entre microservicios/peticiones';


--
-- TOC entry 3705 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.ip_address; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.ip_address IS 'Dirección IP del origen de la acción';


--
-- TOC entry 3706 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.user_agent; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.user_agent IS 'User-Agent del cliente que originó la acción';


--
-- TOC entry 3707 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.metadata; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.metadata IS 'Información adicional en formato JSONB';


--
-- TOC entry 3708 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN audit_logs.created_at; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.audit_logs.created_at IS 'Fecha y hora en que se registró el evento';


--
-- TOC entry 222 (class 1259 OID 16399)
-- Name: audit_logs_id_seq; Type: SEQUENCE; Schema: audit; Owner: postgres
--

CREATE SEQUENCE audit.audit_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE audit.audit_logs_id_seq OWNER TO postgres;

--
-- TOC entry 3709 (class 0 OID 0)
-- Dependencies: 222
-- Name: audit_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: postgres
--

ALTER SEQUENCE audit.audit_logs_id_seq OWNED BY audit.audit_logs.id;


--
-- TOC entry 223 (class 1259 OID 16400)
-- Name: error_logs; Type: TABLE; Schema: audit; Owner: postgres
--

CREATE TABLE audit.error_logs (
                                  id bigint NOT NULL,
                                  level character varying(255) NOT NULL,
                                  service character varying(255) NOT NULL,
                                  message character varying(255) NOT NULL,
                                  exception text,
                                  context jsonb,
                                  correlation_id character varying(255),
                                  created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE audit.error_logs OWNER TO postgres;

--
-- TOC entry 3710 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE error_logs; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON TABLE audit.error_logs IS 'Registro de errores y advertencias para observabilidad del IdP';


--
-- TOC entry 3711 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.id IS 'Identificador único del error';


--
-- TOC entry 3712 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.level; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.level IS 'Nivel del log (ERROR o WARN)';


--
-- TOC entry 3713 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.service; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.service IS 'Servicio donde ocurrió el error';


--
-- TOC entry 3714 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.message; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.message IS 'Mensaje principal del error';


--
-- TOC entry 3715 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.exception; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.exception IS 'Detalle de la excepción o stacktrace';


--
-- TOC entry 3716 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.context; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.context IS 'Contexto adicional en JSONB (request, headers, ids)';


--
-- TOC entry 3717 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.correlation_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.correlation_id IS 'ID de correlación para seguimiento entre servicios';


--
-- TOC entry 3718 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN error_logs.created_at; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.error_logs.created_at IS 'Fecha y hora del registro de error';


--
-- TOC entry 224 (class 1259 OID 16406)
-- Name: error_logs_id_seq; Type: SEQUENCE; Schema: audit; Owner: postgres
--

CREATE SEQUENCE audit.error_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE audit.error_logs_id_seq OWNER TO postgres;

--
-- TOC entry 3719 (class 0 OID 0)
-- Dependencies: 224
-- Name: error_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: postgres
--

ALTER SEQUENCE audit.error_logs_id_seq OWNED BY audit.error_logs.id;


--
-- TOC entry 225 (class 1259 OID 16407)
-- Name: security_events; Type: TABLE; Schema: audit; Owner: postgres
--

CREATE TABLE audit.security_events (
                                       id bigint NOT NULL,
                                       user_id bigint,
                                       event_type character varying(255) NOT NULL,
                                       client_id bigint,
                                       ip_address character varying(255),
                                       user_agent character varying(255),
                                       details jsonb,
                                       created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE audit.security_events OWNER TO postgres;

--
-- TOC entry 3720 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE security_events; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON TABLE audit.security_events IS 'Eventos de seguridad del IdP (login, MFA, tokens)';


--
-- TOC entry 3721 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.id IS 'Identificador único del evento de seguridad';


--
-- TOC entry 3722 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.user_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.user_id IS 'Usuario afectado por el evento (si se conoce)';


--
-- TOC entry 3723 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.event_type; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.event_type IS 'Tipo de evento de seguridad (LOGIN_FAIL, MFA_CHALLENGE, etc.)';


--
-- TOC entry 3724 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.client_id; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.client_id IS 'Cliente/microservicio involucrado en el evento';


--
-- TOC entry 3725 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.ip_address; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.ip_address IS 'IP desde la que se originó el evento';


--
-- TOC entry 3726 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.user_agent; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.user_agent IS 'User-Agent del agente que originó el evento';


--
-- TOC entry 3727 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.details; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.details IS 'Detalles adicionales del evento en JSONB';


--
-- TOC entry 3728 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN security_events.created_at; Type: COMMENT; Schema: audit; Owner: postgres
--

COMMENT ON COLUMN audit.security_events.created_at IS 'Fecha y hora del evento';


--
-- TOC entry 226 (class 1259 OID 16413)
-- Name: security_events_id_seq; Type: SEQUENCE; Schema: audit; Owner: postgres
--

CREATE SEQUENCE audit.security_events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE audit.security_events_id_seq OWNER TO postgres;

--
-- TOC entry 3729 (class 0 OID 0)
-- Dependencies: 226
-- Name: security_events_id_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: postgres
--

ALTER SEQUENCE audit.security_events_id_seq OWNED BY audit.security_events.id;


--
-- TOC entry 227 (class 1259 OID 16414)
-- Name: authorization_codes; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.authorization_codes (
                                          id bigint NOT NULL,
                                          code_hash character varying(255) NOT NULL,
                                          user_id bigint NOT NULL,
                                          client_id bigint NOT NULL,
                                          redirect_uri character varying(255) NOT NULL,
                                          expires_at timestamp without time zone NOT NULL,
                                          used_at timestamp without time zone
);


ALTER TABLE auth.authorization_codes OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16419)
-- Name: authorization_codes_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.authorization_codes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.authorization_codes_id_seq OWNER TO postgres;

--
-- TOC entry 3730 (class 0 OID 0)
-- Dependencies: 228
-- Name: authorization_codes_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.authorization_codes_id_seq OWNED BY auth.authorization_codes.id;


--
-- TOC entry 229 (class 1259 OID 16420)
-- Name: clients; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.clients (
                              id bigint NOT NULL,
                              allowed_grant_types character varying(255),
                              client_id character varying(255) NOT NULL,
                              client_secret_hash character varying(255) NOT NULL,
                              created_at timestamp(6) without time zone,
                              name character varying(255),
                              redirect_uris character varying(255),
                              type character varying(255),
                              updated_at timestamp(6) without time zone
);


ALTER TABLE auth.clients OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16425)
-- Name: clients_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

ALTER TABLE auth.clients ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME auth.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 231 (class 1259 OID 16426)
-- Name: jwk_keys; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.jwk_keys (
                               id bigint NOT NULL,
                               kid character varying(64) NOT NULL,
                               alg character varying(16) NOT NULL,
                               public_jwk jsonb NOT NULL,
                               private_jwk jsonb NOT NULL,
                               active boolean DEFAULT true NOT NULL,
                               created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                               rotated_at timestamp without time zone
);


ALTER TABLE auth.jwk_keys OWNER TO postgres;

--
-- TOC entry 3731 (class 0 OID 0)
-- Dependencies: 231
-- Name: TABLE jwk_keys; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.jwk_keys IS 'Claves JWK utilizadas para firmar y validar JWT, con soporte de rotación';


--
-- TOC entry 3732 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.id IS 'Identificador único de la clave JWK';


--
-- TOC entry 3733 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.kid; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.kid IS 'Key ID (kid) único para identificar la clave';


--
-- TOC entry 3734 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.alg; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.alg IS 'Algoritmo de firma (RS256, ES256, etc.)';


--
-- TOC entry 3735 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.public_jwk; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.public_jwk IS 'Clave pública en formato JWK';


--
-- TOC entry 3736 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.private_jwk; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.private_jwk IS 'Clave privada en formato JWK (cifrada si es posible)';


--
-- TOC entry 3737 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.active; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.active IS 'Indica si la clave está activa para firmar tokens';


--
-- TOC entry 3738 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.created_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.created_at IS 'Fecha de creación de la clave';


--
-- TOC entry 3739 (class 0 OID 0)
-- Dependencies: 231
-- Name: COLUMN jwk_keys.rotated_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.jwk_keys.rotated_at IS 'Fecha en que la clave fue rotada';


--
-- TOC entry 232 (class 1259 OID 16433)
-- Name: jwk_keys_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.jwk_keys_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.jwk_keys_id_seq OWNER TO postgres;

--
-- TOC entry 3740 (class 0 OID 0)
-- Dependencies: 232
-- Name: jwk_keys_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.jwk_keys_id_seq OWNED BY auth.jwk_keys.id;


--
-- TOC entry 233 (class 1259 OID 16434)
-- Name: password_reset_tokens; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.password_reset_tokens (
                                            id bigint NOT NULL,
                                            user_id bigint NOT NULL,
                                            token_hash character varying(255) NOT NULL,
                                            expires_at timestamp without time zone NOT NULL,
                                            used_at timestamp without time zone,
                                            token character varying(255)
);


ALTER TABLE auth.password_reset_tokens OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16439)
-- Name: password_reset_tokens_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.password_reset_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.password_reset_tokens_id_seq OWNER TO postgres;

--
-- TOC entry 3741 (class 0 OID 0)
-- Dependencies: 234
-- Name: password_reset_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.password_reset_tokens_id_seq OWNED BY auth.password_reset_tokens.id;


--
-- TOC entry 235 (class 1259 OID 16440)
-- Name: permissions; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.permissions (
                                  id bigint NOT NULL,
                                  name character varying(255) NOT NULL,
                                  description character varying(255),
                                  created_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE auth.permissions OWNER TO postgres;

--
-- TOC entry 3742 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE permissions; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.permissions IS 'Permisos granulares que definen acciones específicas';


--
-- TOC entry 3743 (class 0 OID 0)
-- Dependencies: 235
-- Name: COLUMN permissions.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.permissions.id IS 'Identificador único del permiso';


--
-- TOC entry 3744 (class 0 OID 0)
-- Dependencies: 235
-- Name: COLUMN permissions.name; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.permissions.name IS 'Nombre del permiso (ej. USER_READ, ORDER_CREATE)';


--
-- TOC entry 3745 (class 0 OID 0)
-- Dependencies: 235
-- Name: COLUMN permissions.description; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.permissions.description IS 'Descripción del permiso';


--
-- TOC entry 236 (class 1259 OID 16445)
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.permissions_id_seq OWNER TO postgres;

--
-- TOC entry 3746 (class 0 OID 0)
-- Dependencies: 236
-- Name: permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.permissions_id_seq OWNED BY auth.permissions.id;


--
-- TOC entry 237 (class 1259 OID 16446)
-- Name: refresh_tokens; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.refresh_tokens (
                                     id bigint NOT NULL,
                                     user_id bigint NOT NULL,
                                     client_id bigint NOT NULL,
                                     token_hash character varying(255),
                                     issued_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     expires_at timestamp without time zone NOT NULL,
                                     revoked_at timestamp without time zone
);


ALTER TABLE auth.refresh_tokens OWNER TO postgres;

--
-- TOC entry 3747 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE refresh_tokens; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.refresh_tokens IS 'Tokens de refresco para usuarios y clientes, rotables y revocables';


--
-- TOC entry 3748 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.id IS 'Identificador único del refresh token';


--
-- TOC entry 3749 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.user_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.user_id IS 'Usuario dueño del token';


--
-- TOC entry 3750 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.client_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.client_id IS 'Cliente (aplicación) asociado al token';


--
-- TOC entry 3751 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.token_hash; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.token_hash IS 'Hash del token, nunca se guarda en claro';


--
-- TOC entry 3752 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.issued_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.issued_at IS 'Fecha de emisión del token';


--
-- TOC entry 3753 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.expires_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.expires_at IS 'Fecha de expiración del token';


--
-- TOC entry 3754 (class 0 OID 0)
-- Dependencies: 237
-- Name: COLUMN refresh_tokens.revoked_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.refresh_tokens.revoked_at IS 'Fecha en que el token fue revocado';


--
-- TOC entry 238 (class 1259 OID 16450)
-- Name: refresh_tokens_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.refresh_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.refresh_tokens_id_seq OWNER TO postgres;

--
-- TOC entry 3755 (class 0 OID 0)
-- Dependencies: 238
-- Name: refresh_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.refresh_tokens_id_seq OWNED BY auth.refresh_tokens.id;


--
-- TOC entry 239 (class 1259 OID 16451)
-- Name: role_permissions; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.role_permissions (
                                       role_id bigint NOT NULL,
                                       permission_id bigint NOT NULL,
                                       granted_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE auth.role_permissions OWNER TO postgres;

--
-- TOC entry 3756 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE role_permissions; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.role_permissions IS 'Relación N:M entre roles y permisos';


--
-- TOC entry 3757 (class 0 OID 0)
-- Dependencies: 239
-- Name: COLUMN role_permissions.role_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.role_permissions.role_id IS 'Rol al que se asigna el permiso';


--
-- TOC entry 3758 (class 0 OID 0)
-- Dependencies: 239
-- Name: COLUMN role_permissions.permission_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.role_permissions.permission_id IS 'Permiso asignado al rol';


--
-- TOC entry 3759 (class 0 OID 0)
-- Dependencies: 239
-- Name: COLUMN role_permissions.granted_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.role_permissions.granted_at IS 'Fecha en que se otorgó el permiso al rol';


--
-- TOC entry 240 (class 1259 OID 16455)
-- Name: roles; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.roles (
                            id bigint NOT NULL,
                            name character varying(255) NOT NULL,
                            description text
);


ALTER TABLE auth.roles OWNER TO postgres;

--
-- TOC entry 3760 (class 0 OID 0)
-- Dependencies: 240
-- Name: TABLE roles; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.roles IS 'Roles del sistema, agrupadores de permisos para usuarios';


--
-- TOC entry 3761 (class 0 OID 0)
-- Dependencies: 240
-- Name: COLUMN roles.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.roles.id IS 'Identificador único del rol';


--
-- TOC entry 3762 (class 0 OID 0)
-- Dependencies: 240
-- Name: COLUMN roles.name; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.roles.name IS 'Nombre del rol (ej. ADMIN, USER)';


--
-- TOC entry 3763 (class 0 OID 0)
-- Dependencies: 240
-- Name: COLUMN roles.description; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.roles.description IS 'Descripción del rol';


--
-- TOC entry 241 (class 1259 OID 16460)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.roles_id_seq OWNER TO postgres;

--
-- TOC entry 3764 (class 0 OID 0)
-- Dependencies: 241
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.roles_id_seq OWNED BY auth.roles.id;


--
-- TOC entry 242 (class 1259 OID 16461)
-- Name: sessions; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.sessions (
                               id bigint NOT NULL,
                               user_id bigint NOT NULL,
                               client_id bigint NOT NULL,
                               ip_address character varying(255),
                               user_agent character varying(255),
                               created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                               terminated_at timestamp without time zone,
                               token_hash character varying(255)
);


ALTER TABLE auth.sessions OWNER TO postgres;

--
-- TOC entry 3765 (class 0 OID 0)
-- Dependencies: 242
-- Name: TABLE sessions; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.sessions IS 'Sesiones activas de usuarios en clientes, útil para auditoría y logout';


--
-- TOC entry 3766 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.id IS 'Identificador único de la sesión';


--
-- TOC entry 3767 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.user_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.user_id IS 'Usuario dueño de la sesión';


--
-- TOC entry 3768 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.client_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.client_id IS 'Cliente (aplicación) asociado a la sesión';


--
-- TOC entry 3769 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.ip_address; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.ip_address IS 'Dirección IP desde la que se inició la sesión';


--
-- TOC entry 3770 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.user_agent; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.user_agent IS 'User-Agent del navegador o aplicación';


--
-- TOC entry 3771 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.created_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.created_at IS 'Fecha de inicio de la sesión';


--
-- TOC entry 3772 (class 0 OID 0)
-- Dependencies: 242
-- Name: COLUMN sessions.terminated_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.sessions.terminated_at IS 'Fecha en que la sesión fue terminada';


--
-- TOC entry 243 (class 1259 OID 16467)
-- Name: sessions_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.sessions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.sessions_id_seq OWNER TO postgres;

--
-- TOC entry 3773 (class 0 OID 0)
-- Dependencies: 243
-- Name: sessions_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.sessions_id_seq OWNED BY auth.sessions.id;


--
-- TOC entry 244 (class 1259 OID 16468)
-- Name: user_roles; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.user_roles (
                                 user_id bigint NOT NULL,
                                 role_id bigint NOT NULL,
                                 assigned_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE auth.user_roles OWNER TO postgres;

--
-- TOC entry 3774 (class 0 OID 0)
-- Dependencies: 244
-- Name: TABLE user_roles; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON TABLE auth.user_roles IS 'Relación N:M entre usuarios y roles';


--
-- TOC entry 3775 (class 0 OID 0)
-- Dependencies: 244
-- Name: COLUMN user_roles.user_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.user_roles.user_id IS 'Usuario asignado al rol';


--
-- TOC entry 3776 (class 0 OID 0)
-- Dependencies: 244
-- Name: COLUMN user_roles.role_id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.user_roles.role_id IS 'Rol asignado al usuario';


--
-- TOC entry 3777 (class 0 OID 0)
-- Dependencies: 244
-- Name: COLUMN user_roles.assigned_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.user_roles.assigned_at IS 'Fecha de asignación del rol al usuario';


--
-- TOC entry 245 (class 1259 OID 16472)
-- Name: users; Type: TABLE; Schema: auth; Owner: postgres
--

CREATE TABLE auth.users (
                            id bigint NOT NULL,
                            username character varying(255) NOT NULL,
                            email character varying(255) NOT NULL,
                            password_hash character varying(255) NOT NULL,
                            is_enable boolean DEFAULT true NOT NULL,
                            two_factor boolean,
                            two_factor_code character varying(255),
                            name character varying(255),
                            lastname character varying(255),
                            identification character varying(255),
                            phone character varying(255),
                            address character varying(255),
                            status character varying(255) DEFAULT 'ACTIVE'::character varying NOT NULL,
                            mfa_enabled boolean DEFAULT false NOT NULL,
                            created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                            two_factor_attempts INTEGER NOT NULL DEFAULT 0
);


ALTER TABLE auth.users OWNER TO postgres;

--
-- TOC entry 3778 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.id; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.id IS 'Pk de usuarios';


--
-- TOC entry 3779 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.username; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.username IS 'Usuario';


--
-- TOC entry 3780 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.email; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.email IS 'Email';


--
-- TOC entry 3781 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.password_hash; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.password_hash IS 'Password encriptado';


--
-- TOC entry 3782 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.status; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.status IS 'Estado --> ACTIVE | LOCKED | INACTIVE';


--
-- TOC entry 3783 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.mfa_enabled; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.mfa_enabled IS 'Autenticación multifactor (MFA)';


--
-- TOC entry 3784 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.created_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.created_at IS 'Fecha de creación';


--
-- TOC entry 3785 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.updated_at; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.updated_at IS 'Fecha de actualización';


--
-- TOC entry 3786 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.is_enable; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.is_enable IS 'Estado TRUE activo, FALSE inactivo';


--
-- TOC entry 3787 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.name; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.name IS 'Nombre de usuario';


--
-- TOC entry 3788 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.lastname; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.lastname IS 'Apellidos';


--
-- TOC entry 3789 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.identification; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.identification IS 'Cedula o Ruc';


--
-- TOC entry 3790 (class 0 OID 0)
-- Dependencies: 245
-- Name: COLUMN users.phone; Type: COMMENT; Schema: auth; Owner: postgres
--

COMMENT ON COLUMN auth.users.phone IS 'Cedula';


--
-- TOC entry 246 (class 1259 OID 16482)
-- Name: users_id_seq; Type: SEQUENCE; Schema: auth; Owner: postgres
--

CREATE SEQUENCE auth.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth.users_id_seq OWNER TO postgres;

--
-- TOC entry 3791 (class 0 OID 0)
-- Dependencies: 246
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: postgres
--

ALTER SEQUENCE auth.users_id_seq OWNED BY auth.users.id;


--
-- TOC entry 247 (class 1259 OID 16483)
-- Name: client_scopes; Type: TABLE; Schema: config; Owner: postgres
--

CREATE TABLE config.client_scopes (
                                      client_id bigint NOT NULL,
                                      scope_id bigint NOT NULL
);


ALTER TABLE config.client_scopes OWNER TO postgres;

--
-- TOC entry 3792 (class 0 OID 0)
-- Dependencies: 247
-- Name: TABLE client_scopes; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON TABLE config.client_scopes IS 'Relación N:M entre clientes y scopes';


--
-- TOC entry 3793 (class 0 OID 0)
-- Dependencies: 247
-- Name: COLUMN client_scopes.client_id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.client_scopes.client_id IS 'Cliente al que se asigna el scope';


--
-- TOC entry 3794 (class 0 OID 0)
-- Dependencies: 247
-- Name: COLUMN client_scopes.scope_id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.client_scopes.scope_id IS 'Scope asignado al cliente';


--
-- TOC entry 248 (class 1259 OID 16486)
-- Name: clients; Type: TABLE; Schema: config; Owner: postgres
--

CREATE TABLE config.clients (
                                id bigint NOT NULL,
                                client_id character varying(80) NOT NULL,
                                client_secret_hash character varying(255),
                                name character varying(120) NOT NULL,
                                type character varying(20) NOT NULL,
                                redirect_uris jsonb,
                                allowed_grant_types jsonb NOT NULL,
                                created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE config.clients OWNER TO postgres;

--
-- TOC entry 3795 (class 0 OID 0)
-- Dependencies: 248
-- Name: TABLE clients; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON TABLE config.clients IS 'Clientes (aplicaciones o microservicios) registrados en el IdP';


--
-- TOC entry 3796 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.id IS 'Identificador único del cliente';


--
-- TOC entry 3797 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.client_id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.client_id IS 'ID único del cliente (ej. app123)';


--
-- TOC entry 3798 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.client_secret_hash; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.client_secret_hash IS 'Hash del secreto del cliente (null si es público)';


--
-- TOC entry 3799 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.name; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.name IS 'Nombre descriptivo del cliente';


--
-- TOC entry 3800 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.type; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.type IS 'Tipo de cliente: confidential o public';


--
-- TOC entry 3801 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.redirect_uris; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.redirect_uris IS 'URIs de redirección permitidas';


--
-- TOC entry 3802 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.allowed_grant_types; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.allowed_grant_types IS 'Tipos de grant permitidos (authorization_code, client_credentials, refresh_token)';


--
-- TOC entry 3803 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.created_at; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.created_at IS 'Fecha de creación del cliente';


--
-- TOC entry 3804 (class 0 OID 0)
-- Dependencies: 248
-- Name: COLUMN clients.updated_at; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.clients.updated_at IS 'Fecha de última actualización del cliente';


--
-- TOC entry 249 (class 1259 OID 16493)
-- Name: clients_id_seq; Type: SEQUENCE; Schema: config; Owner: postgres
--

CREATE SEQUENCE config.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE config.clients_id_seq OWNER TO postgres;

--
-- TOC entry 3805 (class 0 OID 0)
-- Dependencies: 249
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: config; Owner: postgres
--

ALTER SEQUENCE config.clients_id_seq OWNED BY config.clients.id;


--
-- TOC entry 250 (class 1259 OID 16494)
-- Name: policies; Type: TABLE; Schema: config; Owner: postgres
--

CREATE TABLE config.policies (
                                 id bigint NOT NULL,
                                 name character varying(80) NOT NULL,
                                 settings jsonb NOT NULL,
                                 active boolean DEFAULT true NOT NULL,
                                 created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE config.policies OWNER TO postgres;

--
-- TOC entry 3806 (class 0 OID 0)
-- Dependencies: 250
-- Name: TABLE policies; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON TABLE config.policies IS 'Políticas de seguridad y configuración del IdP';


--
-- TOC entry 3807 (class 0 OID 0)
-- Dependencies: 250
-- Name: COLUMN policies.id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.policies.id IS 'Identificador único de la política';


--
-- TOC entry 3808 (class 0 OID 0)
-- Dependencies: 250
-- Name: COLUMN policies.name; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.policies.name IS 'Nombre de la política';


--
-- TOC entry 3809 (class 0 OID 0)
-- Dependencies: 250
-- Name: COLUMN policies.settings; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.policies.settings IS 'Configuración de la política en JSONB (ej. expiración, MFA requerido)';


--
-- TOC entry 3810 (class 0 OID 0)
-- Dependencies: 250
-- Name: COLUMN policies.active; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.policies.active IS 'Indica si la política está activa';


--
-- TOC entry 3811 (class 0 OID 0)
-- Dependencies: 250
-- Name: COLUMN policies.created_at; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.policies.created_at IS 'Fecha de creación de la política';


--
-- TOC entry 251 (class 1259 OID 16501)
-- Name: policies_id_seq; Type: SEQUENCE; Schema: config; Owner: postgres
--

CREATE SEQUENCE config.policies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE config.policies_id_seq OWNER TO postgres;

--
-- TOC entry 3812 (class 0 OID 0)
-- Dependencies: 251
-- Name: policies_id_seq; Type: SEQUENCE OWNED BY; Schema: config; Owner: postgres
--

ALTER SEQUENCE config.policies_id_seq OWNED BY config.policies.id;


--
-- TOC entry 252 (class 1259 OID 16502)
-- Name: scopes; Type: TABLE; Schema: config; Owner: postgres
--

CREATE TABLE config.scopes (
                               id bigint NOT NULL,
                               name character varying(80) NOT NULL,
                               description text
);


ALTER TABLE config.scopes OWNER TO postgres;

--
-- TOC entry 3813 (class 0 OID 0)
-- Dependencies: 252
-- Name: TABLE scopes; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON TABLE config.scopes IS 'Scopes de autorización que definen alcances de acceso';


--
-- TOC entry 3814 (class 0 OID 0)
-- Dependencies: 252
-- Name: COLUMN scopes.id; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.scopes.id IS 'Identificador único del scope';


--
-- TOC entry 3815 (class 0 OID 0)
-- Dependencies: 252
-- Name: COLUMN scopes.name; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.scopes.name IS 'Nombre del scope (ej. users:read, orders:write)';


--
-- TOC entry 3816 (class 0 OID 0)
-- Dependencies: 252
-- Name: COLUMN scopes.description; Type: COMMENT; Schema: config; Owner: postgres
--

COMMENT ON COLUMN config.scopes.description IS 'Descripción del scope';


--
-- TOC entry 253 (class 1259 OID 16507)
-- Name: scopes_id_seq; Type: SEQUENCE; Schema: config; Owner: postgres
--

CREATE SEQUENCE config.scopes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE config.scopes_id_seq OWNER TO postgres;

--
-- TOC entry 3817 (class 0 OID 0)
-- Dependencies: 253
-- Name: scopes_id_seq; Type: SEQUENCE OWNED BY; Schema: config; Owner: postgres
--

ALTER SEQUENCE config.scopes_id_seq OWNED BY config.scopes.id;


--
-- TOC entry 254 (class 1259 OID 16508)
-- Name: roles_tenant; Type: TABLE; Schema: tenant; Owner: postgres
--

CREATE TABLE tenant.roles_tenant (
                                     id bigint NOT NULL,
                                     tenant_id bigint NOT NULL,
                                     name character varying(60) NOT NULL,
                                     description text
);


ALTER TABLE tenant.roles_tenant OWNER TO postgres;

--
-- TOC entry 3818 (class 0 OID 0)
-- Dependencies: 254
-- Name: TABLE roles_tenant; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON TABLE tenant.roles_tenant IS 'Roles específicos definidos dentro de cada tenant';


--
-- TOC entry 3819 (class 0 OID 0)
-- Dependencies: 254
-- Name: COLUMN roles_tenant.id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.roles_tenant.id IS 'Identificador único del rol por tenant';


--
-- TOC entry 3820 (class 0 OID 0)
-- Dependencies: 254
-- Name: COLUMN roles_tenant.tenant_id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.roles_tenant.tenant_id IS 'Tenant al que pertenece el rol';


--
-- TOC entry 3821 (class 0 OID 0)
-- Dependencies: 254
-- Name: COLUMN roles_tenant.name; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.roles_tenant.name IS 'Nombre del rol dentro del tenant';


--
-- TOC entry 3822 (class 0 OID 0)
-- Dependencies: 254
-- Name: COLUMN roles_tenant.description; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.roles_tenant.description IS 'Descripción del rol';


--
-- TOC entry 255 (class 1259 OID 16513)
-- Name: roles_tenant_id_seq; Type: SEQUENCE; Schema: tenant; Owner: postgres
--

CREATE SEQUENCE tenant.roles_tenant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE tenant.roles_tenant_id_seq OWNER TO postgres;

--
-- TOC entry 3823 (class 0 OID 0)
-- Dependencies: 255
-- Name: roles_tenant_id_seq; Type: SEQUENCE OWNED BY; Schema: tenant; Owner: postgres
--

ALTER SEQUENCE tenant.roles_tenant_id_seq OWNED BY tenant.roles_tenant.id;


--
-- TOC entry 256 (class 1259 OID 16514)
-- Name: tenants; Type: TABLE; Schema: tenant; Owner: postgres
--

CREATE TABLE tenant.tenants (
                                id bigint NOT NULL,
                                code character varying(60) NOT NULL,
                                name character varying(120) NOT NULL,
                                status character varying(20) DEFAULT 'ACTIVE'::character varying NOT NULL,
                                created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE tenant.tenants OWNER TO postgres;

--
-- TOC entry 3824 (class 0 OID 0)
-- Dependencies: 256
-- Name: TABLE tenants; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON TABLE tenant.tenants IS 'Organizaciones o tenants que agrupan usuarios y roles';


--
-- TOC entry 3825 (class 0 OID 0)
-- Dependencies: 256
-- Name: COLUMN tenants.id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.tenants.id IS 'Identificador único del tenant';


--
-- TOC entry 3826 (class 0 OID 0)
-- Dependencies: 256
-- Name: COLUMN tenants.code; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.tenants.code IS 'Código único del tenant (ej. ORG001)';


--
-- TOC entry 3827 (class 0 OID 0)
-- Dependencies: 256
-- Name: COLUMN tenants.name; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.tenants.name IS 'Nombre de la organización o tenant';


--
-- TOC entry 3828 (class 0 OID 0)
-- Dependencies: 256
-- Name: COLUMN tenants.status; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.tenants.status IS 'Estado del tenant: ACTIVE, INACTIVE, etc.';


--
-- TOC entry 3829 (class 0 OID 0)
-- Dependencies: 256
-- Name: COLUMN tenants.created_at; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.tenants.created_at IS 'Fecha de creación del tenant';


--
-- TOC entry 257 (class 1259 OID 16519)
-- Name: tenants_id_seq; Type: SEQUENCE; Schema: tenant; Owner: postgres
--

CREATE SEQUENCE tenant.tenants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE tenant.tenants_id_seq OWNER TO postgres;

--
-- TOC entry 3830 (class 0 OID 0)
-- Dependencies: 257
-- Name: tenants_id_seq; Type: SEQUENCE OWNED BY; Schema: tenant; Owner: postgres
--

ALTER SEQUENCE tenant.tenants_id_seq OWNED BY tenant.tenants.id;


--
-- TOC entry 258 (class 1259 OID 16520)
-- Name: user_roles_tenant; Type: TABLE; Schema: tenant; Owner: postgres
--

CREATE TABLE tenant.user_roles_tenant (
                                          user_id bigint NOT NULL,
                                          role_tenant_id bigint NOT NULL
);


ALTER TABLE tenant.user_roles_tenant OWNER TO postgres;

--
-- TOC entry 3831 (class 0 OID 0)
-- Dependencies: 258
-- Name: TABLE user_roles_tenant; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON TABLE tenant.user_roles_tenant IS 'Relación entre usuarios y roles dentro de un tenant';


--
-- TOC entry 3832 (class 0 OID 0)
-- Dependencies: 258
-- Name: COLUMN user_roles_tenant.user_id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.user_roles_tenant.user_id IS 'Usuario asignado al rol dentro del tenant';


--
-- TOC entry 3833 (class 0 OID 0)
-- Dependencies: 258
-- Name: COLUMN user_roles_tenant.role_tenant_id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.user_roles_tenant.role_tenant_id IS 'Rol específico del tenant asignado al usuario';


--
-- TOC entry 259 (class 1259 OID 16523)
-- Name: user_tenants; Type: TABLE; Schema: tenant; Owner: postgres
--

CREATE TABLE tenant.user_tenants (
                                     user_id bigint NOT NULL,
                                     tenant_id bigint NOT NULL
);


ALTER TABLE tenant.user_tenants OWNER TO postgres;

--
-- TOC entry 3834 (class 0 OID 0)
-- Dependencies: 259
-- Name: TABLE user_tenants; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON TABLE tenant.user_tenants IS 'Relación entre usuarios y tenants (multiempresa)';


--
-- TOC entry 3835 (class 0 OID 0)
-- Dependencies: 259
-- Name: COLUMN user_tenants.user_id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.user_tenants.user_id IS 'Usuario asociado al tenant';


--
-- TOC entry 3836 (class 0 OID 0)
-- Dependencies: 259
-- Name: COLUMN user_tenants.tenant_id; Type: COMMENT; Schema: tenant; Owner: postgres
--

COMMENT ON COLUMN tenant.user_tenants.tenant_id IS 'Tenant al que pertenece el usuario';


--
-- TOC entry 3378 (class 2604 OID 16526)
-- Name: audit_logs id; Type: DEFAULT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.audit_logs ALTER COLUMN id SET DEFAULT nextval('audit.audit_logs_id_seq'::regclass);


--
-- TOC entry 3380 (class 2604 OID 16527)
-- Name: error_logs id; Type: DEFAULT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.error_logs ALTER COLUMN id SET DEFAULT nextval('audit.error_logs_id_seq'::regclass);


--
-- TOC entry 3382 (class 2604 OID 16528)
-- Name: security_events id; Type: DEFAULT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.security_events ALTER COLUMN id SET DEFAULT nextval('audit.security_events_id_seq'::regclass);


--
-- TOC entry 3384 (class 2604 OID 16529)
-- Name: authorization_codes id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.authorization_codes ALTER COLUMN id SET DEFAULT nextval('auth.authorization_codes_id_seq'::regclass);


--
-- TOC entry 3385 (class 2604 OID 16530)
-- Name: jwk_keys id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.jwk_keys ALTER COLUMN id SET DEFAULT nextval('auth.jwk_keys_id_seq'::regclass);


--
-- TOC entry 3388 (class 2604 OID 16531)
-- Name: password_reset_tokens id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.password_reset_tokens ALTER COLUMN id SET DEFAULT nextval('auth.password_reset_tokens_id_seq'::regclass);


--
-- TOC entry 3389 (class 2604 OID 16532)
-- Name: permissions id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.permissions ALTER COLUMN id SET DEFAULT nextval('auth.permissions_id_seq'::regclass);


--
-- TOC entry 3390 (class 2604 OID 16533)
-- Name: refresh_tokens id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.refresh_tokens ALTER COLUMN id SET DEFAULT nextval('auth.refresh_tokens_id_seq'::regclass);


--
-- TOC entry 3393 (class 2604 OID 16534)
-- Name: roles id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.roles ALTER COLUMN id SET DEFAULT nextval('auth.roles_id_seq'::regclass);


--
-- TOC entry 3394 (class 2604 OID 16535)
-- Name: sessions id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.sessions ALTER COLUMN id SET DEFAULT nextval('auth.sessions_id_seq'::regclass);


--
-- TOC entry 3397 (class 2604 OID 16536)
-- Name: users id; Type: DEFAULT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.users ALTER COLUMN id SET DEFAULT nextval('auth.users_id_seq'::regclass);


--
-- TOC entry 3403 (class 2604 OID 16537)
-- Name: clients id; Type: DEFAULT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.clients ALTER COLUMN id SET DEFAULT nextval('config.clients_id_seq'::regclass);


--
-- TOC entry 3406 (class 2604 OID 16538)
-- Name: policies id; Type: DEFAULT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.policies ALTER COLUMN id SET DEFAULT nextval('config.policies_id_seq'::regclass);


--
-- TOC entry 3409 (class 2604 OID 16539)
-- Name: scopes id; Type: DEFAULT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.scopes ALTER COLUMN id SET DEFAULT nextval('config.scopes_id_seq'::regclass);


--
-- TOC entry 3410 (class 2604 OID 16540)
-- Name: roles_tenant id; Type: DEFAULT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.roles_tenant ALTER COLUMN id SET DEFAULT nextval('tenant.roles_tenant_id_seq'::regclass);


--
-- TOC entry 3411 (class 2604 OID 16541)
-- Name: tenants id; Type: DEFAULT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.tenants ALTER COLUMN id SET DEFAULT nextval('tenant.tenants_id_seq'::regclass);

-- TOC entry 3837 (class 0 OID 0)
-- Dependencies: 222
-- Name: audit_logs_id_seq; Type: SEQUENCE SET; Schema: audit; Owner: postgres
--

SELECT pg_catalog.setval('audit.audit_logs_id_seq', 1, true);


--
-- TOC entry 3838 (class 0 OID 0)
-- Dependencies: 224
-- Name: error_logs_id_seq; Type: SEQUENCE SET; Schema: audit; Owner: postgres
--

SELECT pg_catalog.setval('audit.error_logs_id_seq', 1, true);


--
-- TOC entry 3839 (class 0 OID 0)
-- Dependencies: 226
-- Name: security_events_id_seq; Type: SEQUENCE SET; Schema: audit; Owner: postgres
--

SELECT pg_catalog.setval('audit.security_events_id_seq', 1, true);


--
-- TOC entry 3840 (class 0 OID 0)
-- Dependencies: 228
-- Name: authorization_codes_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.authorization_codes_id_seq', 1, false);


--
-- TOC entry 3841 (class 0 OID 0)
-- Dependencies: 230
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.clients_id_seq', 1, false);


--
-- TOC entry 3842 (class 0 OID 0)
-- Dependencies: 232
-- Name: jwk_keys_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.jwk_keys_id_seq', 1, false);


--
-- TOC entry 3843 (class 0 OID 0)
-- Dependencies: 234
-- Name: password_reset_tokens_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.password_reset_tokens_id_seq', 1, true);


--
-- TOC entry 3844 (class 0 OID 0)
-- Dependencies: 236
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.permissions_id_seq', 4, false);


--
-- TOC entry 3845 (class 0 OID 0)
-- Dependencies: 238
-- Name: refresh_tokens_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.refresh_tokens_id_seq', 1, false);


--
-- TOC entry 3846 (class 0 OID 0)
-- Dependencies: 241
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.roles_id_seq', 3, true);


--
-- TOC entry 3847 (class 0 OID 0)
-- Dependencies: 243
-- Name: sessions_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.sessions_id_seq', 1, true);


--
-- TOC entry 3848 (class 0 OID 0)
-- Dependencies: 246
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: postgres
--

SELECT pg_catalog.setval('auth.users_id_seq', 1, true);


--
-- TOC entry 3849 (class 0 OID 0)
-- Dependencies: 249
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: config; Owner: postgres
--

SELECT pg_catalog.setval('config.clients_id_seq', 1, false);


--
-- TOC entry 3850 (class 0 OID 0)
-- Dependencies: 251
-- Name: policies_id_seq; Type: SEQUENCE SET; Schema: config; Owner: postgres
--

SELECT pg_catalog.setval('config.policies_id_seq', 1, false);


--
-- TOC entry 3851 (class 0 OID 0)
-- Dependencies: 253
-- Name: scopes_id_seq; Type: SEQUENCE SET; Schema: config; Owner: postgres
--

SELECT pg_catalog.setval('config.scopes_id_seq', 1, false);


--
-- TOC entry 3852 (class 0 OID 0)
-- Dependencies: 255
-- Name: roles_tenant_id_seq; Type: SEQUENCE SET; Schema: tenant; Owner: postgres
--

SELECT pg_catalog.setval('tenant.roles_tenant_id_seq', 1, false);


--
-- TOC entry 3853 (class 0 OID 0)
-- Dependencies: 257
-- Name: tenants_id_seq; Type: SEQUENCE SET; Schema: tenant; Owner: postgres
--

SELECT pg_catalog.setval('tenant.tenants_id_seq', 1, false);


--
-- TOC entry 3415 (class 2606 OID 16543)
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- TOC entry 3421 (class 2606 OID 16545)
-- Name: error_logs error_logs_pkey; Type: CONSTRAINT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.error_logs
    ADD CONSTRAINT error_logs_pkey PRIMARY KEY (id);


--
-- TOC entry 3429 (class 2606 OID 16547)
-- Name: security_events security_events_pkey; Type: CONSTRAINT; Schema: audit; Owner: postgres
--

ALTER TABLE ONLY audit.security_events
    ADD CONSTRAINT security_events_pkey PRIMARY KEY (id);


--
-- TOC entry 3431 (class 2606 OID 16549)
-- Name: authorization_codes authorization_codes_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.authorization_codes
    ADD CONSTRAINT authorization_codes_pkey PRIMARY KEY (id);


--
-- TOC entry 3433 (class 2606 OID 16551)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 3437 (class 2606 OID 16553)
-- Name: jwk_keys jwk_keys_kid_key; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.jwk_keys
    ADD CONSTRAINT jwk_keys_kid_key UNIQUE (kid);


--
-- TOC entry 3439 (class 2606 OID 16555)
-- Name: jwk_keys jwk_keys_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.jwk_keys
    ADD CONSTRAINT jwk_keys_pkey PRIMARY KEY (id);


--
-- TOC entry 3441 (class 2606 OID 16557)
-- Name: password_reset_tokens password_reset_tokens_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.password_reset_tokens
    ADD CONSTRAINT password_reset_tokens_pkey PRIMARY KEY (id);


--
-- TOC entry 3443 (class 2606 OID 16559)
-- Name: permissions permissions_name_key; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.permissions
    ADD CONSTRAINT permissions_name_key UNIQUE (name);


--
-- TOC entry 3445 (class 2606 OID 16561)
-- Name: permissions permissions_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- TOC entry 3448 (class 2606 OID 16563)
-- Name: refresh_tokens refresh_tokens_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pkey PRIMARY KEY (id);


--
-- TOC entry 3451 (class 2606 OID 16565)
-- Name: role_permissions role_permissions_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.role_permissions
    ADD CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id);


--
-- TOC entry 3453 (class 2606 OID 16567)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 3455 (class 2606 OID 16569)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3457 (class 2606 OID 16571)
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- TOC entry 3435 (class 2606 OID 16573)
-- Name: clients uk2og8x0i6lngghy4cqupje9dki; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.clients
    ADD CONSTRAINT uk2og8x0i6lngghy4cqupje9dki UNIQUE (client_id);


--
-- TOC entry 3460 (class 2606 OID 16575)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3462 (class 2606 OID 16579)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3464 (class 2606 OID 16581)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 3466 (class 2606 OID 16583)
-- Name: client_scopes client_scopes_pkey; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.client_scopes
    ADD CONSTRAINT client_scopes_pkey PRIMARY KEY (client_id, scope_id);


--
-- TOC entry 3468 (class 2606 OID 16585)
-- Name: clients clients_client_id_key; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.clients
    ADD CONSTRAINT clients_client_id_key UNIQUE (client_id);


--
-- TOC entry 3470 (class 2606 OID 16587)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 3472 (class 2606 OID 16589)
-- Name: policies policies_name_key; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.policies
    ADD CONSTRAINT policies_name_key UNIQUE (name);


--
-- TOC entry 3474 (class 2606 OID 16591)
-- Name: policies policies_pkey; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.policies
    ADD CONSTRAINT policies_pkey PRIMARY KEY (id);


--
-- TOC entry 3476 (class 2606 OID 16593)
-- Name: scopes scopes_name_key; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.scopes
    ADD CONSTRAINT scopes_name_key UNIQUE (name);


--
-- TOC entry 3478 (class 2606 OID 16595)
-- Name: scopes scopes_pkey; Type: CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.scopes
    ADD CONSTRAINT scopes_pkey PRIMARY KEY (id);


--
-- TOC entry 3480 (class 2606 OID 16597)
-- Name: roles_tenant roles_tenant_pkey; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.roles_tenant
    ADD CONSTRAINT roles_tenant_pkey PRIMARY KEY (id);


--
-- TOC entry 3482 (class 2606 OID 16599)
-- Name: roles_tenant roles_tenant_tenant_id_name_key; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.roles_tenant
    ADD CONSTRAINT roles_tenant_tenant_id_name_key UNIQUE (tenant_id, name);


--
-- TOC entry 3484 (class 2606 OID 16601)
-- Name: tenants tenants_code_key; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.tenants
    ADD CONSTRAINT tenants_code_key UNIQUE (code);


--
-- TOC entry 3486 (class 2606 OID 16603)
-- Name: tenants tenants_pkey; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.tenants
    ADD CONSTRAINT tenants_pkey PRIMARY KEY (id);


--
-- TOC entry 3488 (class 2606 OID 16605)
-- Name: user_roles_tenant user_roles_tenant_pkey; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_roles_tenant
    ADD CONSTRAINT user_roles_tenant_pkey PRIMARY KEY (user_id, role_tenant_id);


--
-- TOC entry 3490 (class 2606 OID 16607)
-- Name: user_tenants user_tenants_pkey; Type: CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_tenants
    ADD CONSTRAINT user_tenants_pkey PRIMARY KEY (user_id, tenant_id);


--
-- TOC entry 3416 (class 1259 OID 16608)
-- Name: idx_audit_logs_action; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_audit_logs_action ON audit.audit_logs USING btree (action);


--
-- TOC entry 3417 (class 1259 OID 16609)
-- Name: idx_audit_logs_actor_time; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_audit_logs_actor_time ON audit.audit_logs USING btree (actor_user_id, created_at);


--
-- TOC entry 3418 (class 1259 OID 16610)
-- Name: idx_audit_logs_corr; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_audit_logs_corr ON audit.audit_logs USING btree (correlation_id);


--
-- TOC entry 3419 (class 1259 OID 16611)
-- Name: idx_audit_logs_target; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_audit_logs_target ON audit.audit_logs USING btree (target_type, target_id);


--
-- TOC entry 3422 (class 1259 OID 16612)
-- Name: idx_error_logs_corr; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_error_logs_corr ON audit.error_logs USING btree (correlation_id);


--
-- TOC entry 3423 (class 1259 OID 16613)
-- Name: idx_error_logs_level; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_error_logs_level ON audit.error_logs USING btree (level);


--
-- TOC entry 3424 (class 1259 OID 16614)
-- Name: idx_error_logs_service_time; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_error_logs_service_time ON audit.error_logs USING btree (service, created_at);


--
-- TOC entry 3425 (class 1259 OID 16615)
-- Name: idx_sec_events_client; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_sec_events_client ON audit.security_events USING btree (client_id);


--
-- TOC entry 3426 (class 1259 OID 16616)
-- Name: idx_sec_events_type; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_sec_events_type ON audit.security_events USING btree (event_type);


--
-- TOC entry 3427 (class 1259 OID 16617)
-- Name: idx_sec_events_user_time; Type: INDEX; Schema: audit; Owner: postgres
--

CREATE INDEX idx_sec_events_user_time ON audit.security_events USING btree (user_id, created_at);


--
-- TOC entry 3446 (class 1259 OID 16618)
-- Name: refresh_tokens_expires_at_idx; Type: INDEX; Schema: auth; Owner: postgres
--

CREATE INDEX refresh_tokens_expires_at_idx ON auth.refresh_tokens USING btree (expires_at);


--
-- TOC entry 3449 (class 1259 OID 16619)
-- Name: refresh_tokens_user_id_client_id_idx; Type: INDEX; Schema: auth; Owner: postgres
--

CREATE INDEX refresh_tokens_user_id_client_id_idx ON auth.refresh_tokens USING btree (user_id, client_id);


--
-- TOC entry 3458 (class 1259 OID 16620)
-- Name: sessions_user_id_client_id_idx; Type: INDEX; Schema: auth; Owner: postgres
--

CREATE INDEX sessions_user_id_client_id_idx ON auth.sessions USING btree (user_id, client_id);


--
-- TOC entry 3491 (class 2606 OID 16621)
-- Name: authorization_codes authorization_codes_client_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.authorization_codes
    ADD CONSTRAINT authorization_codes_client_id_fkey FOREIGN KEY (client_id) REFERENCES config.clients(id) ON DELETE CASCADE;


--
-- TOC entry 3492 (class 2606 OID 16626)
-- Name: authorization_codes authorization_codes_user_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.authorization_codes
    ADD CONSTRAINT authorization_codes_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- TOC entry 3493 (class 2606 OID 16631)
-- Name: password_reset_tokens password_reset_tokens_user_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.password_reset_tokens
    ADD CONSTRAINT password_reset_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id);


--
-- TOC entry 3494 (class 2606 OID 16636)
-- Name: refresh_tokens refresh_tokens_client_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.refresh_tokens
    ADD CONSTRAINT refresh_tokens_client_id_fkey FOREIGN KEY (client_id) REFERENCES config.clients(id) ON DELETE CASCADE;


--
-- TOC entry 3495 (class 2606 OID 16641)
-- Name: refresh_tokens refresh_tokens_user_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.refresh_tokens
    ADD CONSTRAINT refresh_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- TOC entry 3496 (class 2606 OID 16646)
-- Name: role_permissions role_permissions_permission_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.role_permissions
    ADD CONSTRAINT role_permissions_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES auth.permissions(id) ON DELETE CASCADE;


--
-- TOC entry 3497 (class 2606 OID 16651)
-- Name: role_permissions role_permissions_role_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.role_permissions
    ADD CONSTRAINT role_permissions_role_id_fkey FOREIGN KEY (role_id) REFERENCES auth.roles(id) ON DELETE CASCADE;


--
-- TOC entry 3498 (class 2606 OID 16656)
-- Name: sessions sessions_client_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.sessions
    ADD CONSTRAINT sessions_client_id_fkey FOREIGN KEY (client_id) REFERENCES auth.clients(id);


--
-- TOC entry 3499 (class 2606 OID 16661)
-- Name: sessions sessions_user_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.sessions
    ADD CONSTRAINT sessions_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- TOC entry 3500 (class 2606 OID 16666)
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES auth.roles(id) ON DELETE CASCADE;


--
-- TOC entry 3501 (class 2606 OID 16671)
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: auth; Owner: postgres
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- TOC entry 3502 (class 2606 OID 16676)
-- Name: client_scopes client_scopes_client_id_fkey; Type: FK CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.client_scopes
    ADD CONSTRAINT client_scopes_client_id_fkey FOREIGN KEY (client_id) REFERENCES config.clients(id) ON DELETE CASCADE;


--
-- TOC entry 3503 (class 2606 OID 16681)
-- Name: client_scopes client_scopes_scope_id_fkey; Type: FK CONSTRAINT; Schema: config; Owner: postgres
--

ALTER TABLE ONLY config.client_scopes
    ADD CONSTRAINT client_scopes_scope_id_fkey FOREIGN KEY (scope_id) REFERENCES config.scopes(id) ON DELETE CASCADE;


--
-- TOC entry 3504 (class 2606 OID 16686)
-- Name: roles_tenant roles_tenant_tenant_id_fkey; Type: FK CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.roles_tenant
    ADD CONSTRAINT roles_tenant_tenant_id_fkey FOREIGN KEY (tenant_id) REFERENCES tenant.tenants(id) ON DELETE CASCADE;


--
-- TOC entry 3505 (class 2606 OID 16691)
-- Name: user_roles_tenant user_roles_tenant_role_tenant_id_fkey; Type: FK CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_roles_tenant
    ADD CONSTRAINT user_roles_tenant_role_tenant_id_fkey FOREIGN KEY (role_tenant_id) REFERENCES tenant.roles_tenant(id) ON DELETE CASCADE;


--
-- TOC entry 3506 (class 2606 OID 16696)
-- Name: user_roles_tenant user_roles_tenant_user_id_fkey; Type: FK CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_roles_tenant
    ADD CONSTRAINT user_roles_tenant_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- TOC entry 3507 (class 2606 OID 16701)
-- Name: user_tenants user_tenants_tenant_id_fkey; Type: FK CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_tenants
    ADD CONSTRAINT user_tenants_tenant_id_fkey FOREIGN KEY (tenant_id) REFERENCES tenant.tenants(id) ON DELETE CASCADE;


--
-- TOC entry 3508 (class 2606 OID 16706)
-- Name: user_tenants user_tenants_user_id_fkey; Type: FK CONSTRAINT; Schema: tenant; Owner: postgres
--

ALTER TABLE ONLY tenant.user_tenants
    ADD CONSTRAINT user_tenants_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE;


CREATE TABLE user_consents (  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               consent_type VARCHAR(50) NOT NULL,
                               version VARCHAR(20) NOT NULL,
                               document_path VARCHAR(255),
                               document_hash VARCHAR(64),
                               action VARCHAR(20) CHECK (action IN ('ACCEPTED', 'REJECTED')),
                               user_agent TEXT,
                               ip_address VARCHAR(45),
                               is_accepted BOOLEAN NOT NULL,
                               accepted_at TIMESTAMPTZ,
                               created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_user_consent
                                FOREIGN KEY (user_id)
                                REFERENCES users(id)
                                ON DELETE CASCADE
);

CREATE INDEX idx_user_consents_user ON user_consents(user_id);
CREATE INDEX idx_user_consents_type ON user_consents(consent_type);



