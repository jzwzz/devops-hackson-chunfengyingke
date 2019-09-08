import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './activity-info.reducer';
import { IActivityInfo } from 'app/shared/model/activity-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IActivityInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IActivityInfoUpdateState {
  isNew: boolean;
}

export class ActivityInfoUpdate extends React.Component<IActivityInfoUpdateProps, IActivityInfoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { activityInfoEntity } = this.props;
      const entity = {
        ...activityInfoEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/activity-info');
  };

  render() {
    const { activityInfoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="chunfengApp.activityInfo.home.createOrEditLabel">
              <Translate contentKey="chunfengApp.activityInfo.home.createOrEditLabel">Create or edit a ActivityInfo</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : activityInfoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="activity-info-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="activity-info-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="activityNameLabel" for="activity-info-activityName">
                    <Translate contentKey="chunfengApp.activityInfo.activityName">Activity Name</Translate>
                  </Label>
                  <AvField id="activity-info-activityName" type="text" name="activityName" />
                </AvGroup>
                <AvGroup>
                  <Label id="userLabel" for="activity-info-user">
                    <Translate contentKey="chunfengApp.activityInfo.user">User</Translate>
                  </Label>
                  <AvField id="activity-info-user" type="text" name="user" />
                </AvGroup>
                <AvGroup>
                  <Label id="telephoneLabel" for="activity-info-telephone">
                    <Translate contentKey="chunfengApp.activityInfo.telephone">Telephone</Translate>
                  </Label>
                  <AvField id="activity-info-telephone" type="text" name="telephone" />
                </AvGroup>
                <AvGroup>
                  <Label id="payStatusLabel" for="activity-info-payStatus">
                    <Translate contentKey="chunfengApp.activityInfo.payStatus">Pay Status</Translate>
                  </Label>
                  <AvField id="activity-info-payStatus" type="text" name="payStatus" />
                </AvGroup>
                <AvGroup>
                  <Label id="addDateLabel" for="activity-info-addDate">
                    <Translate contentKey="chunfengApp.activityInfo.addDate">Add Date</Translate>
                  </Label>
                  <AvField id="activity-info-addDate" type="text" name="addDate" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/activity-info" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  activityInfoEntity: storeState.activityInfo.entity,
  loading: storeState.activityInfo.loading,
  updating: storeState.activityInfo.updating,
  updateSuccess: storeState.activityInfo.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivityInfoUpdate);
